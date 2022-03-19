package sparkpractice

import org.apache.spark.sql._
import org.apache.log4j._

object SparkSQLDataSet {
  case class Person(id: Int, name: String, age: Int, friends: Int)


  def main(args: Array[String]) {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Use new SparkSession interface in Spark 2.0
    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName("SparkSQLDataset")
      .getOrCreate()

    import spark.implicits._
    val inputSchema = Encoders.product[Person].schema
    
     val schemaPeople = spark.read
       .option("delimiter", ",")
     .option("inference", "true").schema(inputSchema)
      .csv("fakefriends.csv")
       
     
    // Infer the schema, and register the DataSet as a table.

   schemaPeople.printSchema()

    schemaPeople.createOrReplaceTempView("people")

    // SQL can be run over DataFrames that have been registered as a table
    val teenagers = spark.sql("SELECT * FROM people WHERE age >= 13 AND age <= 19")
    
    teenagers.show(false)
    //val results = teenagers.collect()

    //results.foreach(println)
   // spark.stop()
  }
}