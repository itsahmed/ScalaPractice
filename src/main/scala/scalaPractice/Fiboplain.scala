package scalaPractice

object Fiboplain {
  def main(args: Array[String]): Unit = {
    var a = 1
    var b = 1
    var t = 1
    val n = scala.io.StdIn.readInt()
    print(a +","+ b)
    for ( i <- 1 to n){
      t = a
      a = b
      b = a+t
      print(",")
      print(b)
      
      
    }
  }
}