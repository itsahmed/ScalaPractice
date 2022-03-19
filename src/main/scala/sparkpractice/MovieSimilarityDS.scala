package sparkpractice

import org.apache.spark.sql.functions._
import org.apache.log4j._
import org.apache.spark.sql.{DataFrame,Dataset, Encoders, SparkSession}
import org.apache.spark.sql.types.{IntegerType, LongType, StringType, StructType}

object MovieSimilarityDS {

  case class Movies(userid: Int, movieid: Int, rating: Int,timestamp: Long)
  case class MoviesNames(movieid: Int, movieTitle: String)
  case class MoviePairs(movie1: Int,movie2: Int, rating1: Int, rating2: Int)
  case class MoviePairsSimilarity(movie1: Int,movie2: Int, score: Double, numPairs: Long)

  def computecosineSimilarrity(spark: SparkSession, data: Dataset[MoviePairs]): Dataset[MoviePairsSimilarity] = {

    val pairscores = data
      .withColumn("xx", col("rating1") * col("rating1"))
      .withColumn("yy", col("rating2") * col("rating2"))
      .withColumn("xy", col("rating1") * col("rating2"))

    val calculatesimilarity = pairscores
      .groupBy("movie1", "movie2")
      .agg(
        sum(col("xy")).alias("numerator"),
        (sqrt(sum(col("xx"))) * sqrt(sum(col("yy")))).alias("denominator"),
        count(col("xy")).alias("numPairs")
      )
      calculatesimilarity.show(false)
    import spark.implicits._
    val result = calculatesimilarity
      .withColumn("score",
        when(col("denominator") =!= 0, col("numerator") / col("denominator"))
      .otherwise(null)
      ).select("movie1", "movie2", "score", "numPairs").as[MoviePairsSimilarity]

    result

  }
  def getMovieName(moviesNames:DataFrame, movieid: Int): String = {
    val result = moviesNames.filter(col("movieid") === movieid)
      .select("movieTitle").collect()(0)

    result(0).toString
  }

  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    System.setProperty("hadoop.home.dir", "C:\\winutils")

    val spark = SparkSession
      .builder()
      .appName("MovieSimilarityDS")
      .master("local[*]")
      .getOrCreate()

    val moviesnamesschema = new StructType()
      .add("movieid",IntegerType, nullable = true)
      .add("movieTitle", StringType, nullable = true)

    val moviesschema = new StructType()
      .add("userid", IntegerType,nullable = true)
      .add("movieid", IntegerType,nullable = true)
      .add("rating", IntegerType,nullable = true)
      .add("timestamp", LongType,nullable = true)
    println("\nLoading movie names...")

    import spark.implicits._
    val inputSchema = Encoders.product[Movies].schema
    val moviesNames = spark.read
      .option("sep", "|")
      .option("charset", "ISO-8859-1")
      .schema(moviesnamesschema)
      .csv("u.item")
    moviesNames.show(false)  

    val movie = spark.read
      .option("sep", "\t")
      .schema(moviesschema)
      .csv("u.data")
     movie.show(false) 

    val ratings = movie.select("userid", "movieid", "rating")

    val moviePairs = ratings.as("ratings1")
      .join(ratings.as("ratings2"), $"ratings1.userid" === $"ratings2.userid" && $"ratings1.movieid" < $"ratings2.movieid")
      .select($"ratings1.movieid".alias("movie1"),
        $"ratings2.movieid".alias("movie2"),
        $"ratings1.rating".alias("rating1"),
        $"ratings2.rating".alias("rating2")
      ).as[MoviePairs]

    val moviepairsimilarities = computecosineSimilarrity(spark, moviePairs).cache()
    moviepairsimilarities.show(false)

    if (args.length > 0) {
      val scorethreshold = 0.97
      val coOccurencethreshold = 50.0

      val movieid: Int = args(0).toInt

      val filteredresults = moviepairsimilarities.filter(
        (col("movie1") === movieid || col("movie2") === movieid) &&
          col("score") > scorethreshold && col("numPairs") > coOccurencethreshold
      )

      val results = filteredresults.sort(col("score").desc).take(10)


      println("\nTop 10 similar movies for " + getMovieName(moviesNames, movieid))
      for (result <- results) {

        var similarmovieid = result.movie1
        if (similarmovieid == movieid) {
          similarmovieid = result.movie2
        }
        println(getMovieName(moviesNames, similarmovieid) + "\tscore: " + result.score + "\tstrength: " + result.numPairs)
      }

    }



  }
}