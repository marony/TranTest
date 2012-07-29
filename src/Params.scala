// Java(画面)⇔Scala(ロジック)パラメータ受渡クラス
class Params(val database : Int, val host : String, val table : String, val user : String, val password : String,
    val transaction_type : Boolean, val isolation_level : Int, val for_update : Boolean,
    val loop_count : Int, val thread_count : Int, javaBalance : java.math.BigDecimal, javaCommute : java.math.BigDecimal) {
  val balance = BigDecimal.javaBigDecimal2bigDecimal(javaBalance)
  val commute = BigDecimal.javaBigDecimal2bigDecimal(javaCommute)
}
