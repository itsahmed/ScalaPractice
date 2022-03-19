package exregex

object pnum {
  def main(args: Array[String]): Unit = {
    for(i <- 0 to 9){
      for(j <- 0 to 9){
        for(k <- 0 to 9){
          for(l <-0 to 9){
            for(m <- 0 to 9){
              println(s"9437${i}${j}${k}${l}${m}2")
            }
          }
        }
      }
    }
  }
}