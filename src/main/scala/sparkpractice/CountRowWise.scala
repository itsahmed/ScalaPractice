package sparkpractice

import org.apache.spark.sql.SparkSession
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

object CountRowWise {
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    System.setProperty("hadoop.home.dir", "C:\\winutils")

    val spark = SparkSession
      .builder()
      .appName("CountRowWise")
      .master("local[*]")
      .getOrCreate()
      
    var idf =  spark.createDataFrame(Seq(
        (true,true,true,true),
        (true,true,false,false),
        (true,false,false,false),
        (false,false,false,false))).toDF("a","b","c","d") 
      
  
  idf = idf.withColumn("id",monotonically_increasing_id).withColumn("id",row_number().over(Window.orderBy("id")))
  idf.show(false)
  
  val cols = idf.columns.toList
  
  val ordd = idf.rdd.map (row => {
    var t =0 
    var f = 0
    for(field <- cols){
      if(row.getAs(field).equals(true)) t += 1 else if (row.getAs(field).equals(false)) f += 1
    }
    (row.getAs("id").toString,t,f)
  })
  val odf = spark.createDataFrame(ordd).toDF("id","trueCount","falseCount")
  
  odf.show(false)
  
  val fdf = idf.join(odf, Seq("id")).drop("id")
  fdf.show(false)    
      
  
  }
}