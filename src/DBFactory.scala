import java.sql.{DriverManager, Connection, Statement, ResultSet,SQLException}

// データベースの製品種別
object DB extends Enumeration {
  type DB = Value
  val MySQL = Value
}

// データベースのトランザクション分離レベル
object IsolationLevel extends Enumeration {
  type IsolationLevel = Value
  val ReadUncommitted, ReadCommitted, RepeatableRead, Serializable = Value
}

// データベース依存ロジック系ファクトリクラス
object DBFactory {
  def getConnection(database : DB.DB, isolation_level : Int, table : String, user : String, password : String) : Connection = {
    try {
      database match {
        case DB.MySQL => {
      	Class.forName("com.mysql.jdbc.Driver").newInstance();
          val connection = DriverManager.getConnection("jdbc:mysql://localhost/" + table + "?" + "user=" + user + "&passwor=" + password);
          val stmt = connection.createStatement()
          // FIXME: JDBCのdefaultIsolationLevelを使用する
          var query = "SET SESSION TRANSACTION ISOLATION LEVEL "
          IsolationLevel(isolation_level) match {
            case IsolationLevel.ReadUncommitted => query += "READ UNCOMMITTED;"
            case IsolationLevel.ReadCommitted => query += "READ COMMITTED;"
            case IsolationLevel.RepeatableRead => query += "REPEATABLE READ;"
            case IsolationLevel.Serializable => query += "SERIALIZABLE;"
          }
          stmt.executeQuery(query)
          return	connection
        }
      }
    } catch {
      case e => println("Database error "+e)
    }
    return	null
  }
  def getDAO(dbType : DB.DB, connection : Connection) : DAO = {
    dbType match {
      case DB.MySQL => {
        return	new MySQLDAO(connection)
      }
    }
    return	null
  }
  def getTransactionLogic(dbType : DB.DB, transactionType : Boolean, connection : Connection) : TransactionLogic = {
    if (transactionType) {
      dbType match {
        case DB.MySQL => {
          return	new MySQLTransactionLogic(connection)
        }
      }
    }
    return	new NoTransactionLogic(connection)
  }
}
