package scalaPractice

object RemoveDuplicates {
  def main(args: Array[String]): Unit = {
    val input = "ccccdddeeeegggkkk"
    val car = input.toCharArray().distinct
    println(removeDuplicates(input))
    println(car.mkString(""))
  }
  
  def removeDuplicates(inStr : String): String = {
    var res = ""
    for(chr <- inStr if !res.contains(chr)){
      res = res + chr
    }
    res
  }
}