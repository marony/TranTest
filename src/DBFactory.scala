import java.sql.{DriverManager, Connection, Statement, ResultSet,SQLException}

object DB extends Enumeration {
  type DB = Value
  val MySQL = Value
}

object DBFactory {
  def getConnection(dbType : DB.DB, isolation_level : Int, table : String, user : String, password : String) : Connection = {
    try {
	    dbType match {
	      case DB.MySQL => {
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	        val connection = DriverManager.getConnection("jdbc:mysql://localhost/" + table + "?" + "user=" + user + "&passwor=" + password);
	        val stmt = connection.createStatement()
	        var query = "SET SESSION TRANSACTION ISOLATION LEVEL "
	        isolation_level match {
	          case 0 => query += "READ UNCOMMITTED;"
	          case 1 => query += "READ COMMITTED;"
	          case 2 => query += "REPEATABLE READ;"
	          case 3 => query += "SERIALIZABLE;"
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
