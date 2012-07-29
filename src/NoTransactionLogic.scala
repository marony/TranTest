import java.sql.{DriverManager, Connection, Statement, ResultSet,SQLException}

// 無トランザクション用トランザクション制御クラス
class NoTransactionLogic(val connection : Connection) extends TransactionLogic {
  def beginTran : Unit = {}
  def commit : Unit = {}
  def rollback : Unit = {}
}