import java.sql.{DriverManager, Connection, Statement, ResultSet,SQLException}

// データベースアクセス基底トレイト
trait DAO {
  def executeQuery(query : String) : (Statement, ResultSet);
  def executeUpdate(query: String): Unit;
}