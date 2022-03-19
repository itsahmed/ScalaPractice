package scalaPractice

object FilterVowelsAndConsonants {
  def main(args: Array[String]): Unit = {
    val input = "My name is Zaheer Ahmed"
    println(filterVowels1(input))
    println()
    println(filterCons1(input))
    println()
    println(filterVowels2(input))
    println()
    println(filterCons2(input))
    println()
    println(filterVowels3(input))
    println()
    println(filterCons3(input))
  }
  val vowelsList = List('a','e','i','o','u')
  def filterVowels1(inStr: String): String = {
    var res = ""
    for( chr <- inStr){
      if (! vowelsList.contains(chr)){
        res = res + chr
      }
    }
   res
  }
  
  def filterCons1(inStr: String): String = {
    var res = ""
    for( chr <- inStr){
      if (vowelsList.contains(chr) || chr == ' '){
        res = res + chr
      }
    }
   res
  }
  def filterVowels2(inStr: String): String = {
    var res = ""
    for( chr <- inStr if ! vowelsList.contains(chr)){
      res = res + chr
    }
   res
  }
  def filterCons2(inStr: String): String = {
    var res = ""
    for( chr <- inStr if vowelsList.contains(chr) || chr == ' '){    
        res = res + chr
    }
   res
  }
  def filterVowels3(inStr: String): String = {
    var res = inStr.replaceAll("[aeiouAEIOU]", "")
    res
  }
  def filterCons3(inStr: String): String = {
    var res = inStr.replaceAll("[^aeiouAEIOU ]", "")
    res
  }
}