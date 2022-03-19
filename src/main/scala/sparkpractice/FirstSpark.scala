package sparkpractice

import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.apache.log4j.Level

object FirstSpark {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\winutils")
    Logger.getLogger("org").setLevel(Level.WARN)
    val spark = SparkSession.builder.master("local[2]").appName("ex").getOrCreate()
    
    val df = spark.createDataFrame(Seq(
        (1,"zay"))).toDF("id","name")
        
        df.show()
  }
}