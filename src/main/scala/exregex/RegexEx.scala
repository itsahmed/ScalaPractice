package exregex

import scala.util.matching.Regex



object RegexEx {
  def main(args: Array[String]): Unit = {
    val str= "(S|s)cala".r
    
    val nump = "[0-9a-zA-Z]+".r
    
    val sen = "Scal1a is scala2ble language"
    
    val re = nump.findAllIn(sen)
    
    
    re.foreach(println)
  }
}