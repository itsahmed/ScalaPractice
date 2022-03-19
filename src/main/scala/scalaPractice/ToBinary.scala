package scalaPractice

object ToBinary {
  def main(args: Array[String]): Unit = {
    println(toBinary(101))
  }

  def toBinary(dec: Int): String = {
    var r = 0
    var q = 0
    var bin = ""
    var flag = true
    var vari = dec
    while (flag) {
      if (q == 1) {
        //bin = bin.reverse
        flag = false
      } else {
        q = vari / 2
        r = vari % 2
        bin = bin + r.toString()
        vari = vari / 2
      }
    }
    q.toString() + bin.reverse
  }



}