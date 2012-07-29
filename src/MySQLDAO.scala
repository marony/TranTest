import java.sql.{DriverManager, Connection, Statement, ResultSet,SQLException}

// MySQL用データベースアクセスクラス
class MySQLDAO(val connection : Connection) extends DAO {
  def executeQuery(query: String): (Statement, ResultSet) = {
    val	stmt = connection.createStatement()
    return	(stmt, stmt.executeQuery(query))
  }
  def executeUpdate(query: String): Unit = {
    val	stmt = connection.createStatement()
    stmt.executeUpdate(query)
    stmt.close()
  }
}