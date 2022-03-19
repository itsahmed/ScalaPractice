package exregex

object testing {
  def main(args: Array[String]): Unit = {
    
    val s1= "00-44 48 5555 8361"
    val s2 = "0 - 22 1985--324"
    val s3 ="555372654"
    
    /*var re = s1.replaceAll("[- ]", "")
    
    
    println(re)
    
    var slen = re.length()
    var sre =""
    for (i <-1 to slen){
      if(i %3 ==0) {
        sre =sre + re(i-1)+"-"
      }else {
        sre = sre +re(i-1).toString
      }
      
      
    }
    println(sre)*/
    
    
    val sen = "We test coders. Give    us a try?"
    
    val sensp = sen.split("[.?!]")
    
    sensp.foreach(println)
    
    val re = sensp.map( e => e.trim().split("\\s+").length).max
    
    //println(re)
    
    val st ="positive"
   // println(st(:2))
    
  }
}