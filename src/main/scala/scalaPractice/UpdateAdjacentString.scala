package scalaPractice

object UpdateAdjacentString {
  def main(args: Array[String]): Unit = {
    val input = "abccabc"
    println(updateString(input))
  }
  
  def updateString(inStr : String) :String = {
    var prev= '0'
    var res = ""
    for( index <- 0 until inStr.size if inStr(index) != prev) {
      res = res + inStr(index)
      prev = inStr(index)
    }      
   res
  }
}