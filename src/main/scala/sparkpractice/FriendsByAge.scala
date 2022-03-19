package sparkpractice

import org.apache.spark.SparkContext
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession


object FriendsByAge {
  def parseLine(line: String): (Int, Int) = {
 val fields = line.split(",")
    val age = fields(2).toInt
    val numFriends = fields(3).toInt
    (age, numFriends)
  }



  def main(args: Array[String]): Unit = {
Logger.getLogger("org").setLevel(Level.ERROR)
 /* val conf = new SparkConf().setAppName("FriendsByAge").setMaster("local[*]")
    val sc = new SparkContext(conf)*/
val spark = SparkSession.builder.master("local[*]").getOrCreate()
    val lines = spark.sparkContext.textFile("friends.csv")
    val rdd = lines.map(parseLine)
    val totalsByAge = rdd.mapValues(x => (x, 1)).reduceByKey((x,y) => (x._1 + y._1, x._2 + y._2))
    val averagesByAge = totalsByAge.mapValues(x => x._1.toFloat / x._2)
    val results = averagesByAge.collect()
    results.sorted.foreach(println)
  }
}