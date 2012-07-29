import java.sql.{DriverManager, Connection, Statement, ResultSet,SQLException}

// MySQL用トランザクション制御クラス
class MySQLTransactionLogic(val connection : Connection) extends TransactionLogic {
  connection.setAutoCommit(false);

  def beginTran(): Unit = {}

  def commit(): Unit = {
    connection.commit()
  }

  def rollback(): Unit = {
    connection.rollback()
  }
}