import java.sql.{DriverManager, Connection, Statement, ResultSet,SQLException}

class NoTransactionLogic(val connection : Connection) extends TransactionLogic {
  def beginTran : Unit = {}
  def commit : Unit = {}
  def rollback : Unit = {}
}