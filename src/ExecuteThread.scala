import java.sql.{DriverManager, Connection, Statement, ResultSet,SQLException}

class ExecuteThread(val params : Params) extends Thread {
  override def run() : Unit = {
    // 接続
    val connection = DBFactory.getConnection(DB(params.database), params.isolation_level, params.table, params.user, params.password)
    val dao = DBFactory.getDAO(DB.MySQL, connection)
    val transaction = DBFactory.getTransactionLogic(DB.MySQL, params.transaction_type, connection)
    try {
      // ロジック実行
      dao.executeUpdate("USE trantest;")
      for (i <- 1 to params.loop_count) {
        transaction.beginTran
        // 口座1の残高読み込み
        var query = "SELECT amount FROM transaction WHERE id = 1"
        if (params.for_update) {
          query += " FOR UPDATE;"
        } else {
          query += ";"
        }
        var result : (Statement, ResultSet) = dao.executeQuery(query)
        var stmt : Statement = result._1
        var rs : ResultSet = result._2
        var amount : BigDecimal = BigDecimal(0)
        if (rs.next())
          amount = rs.getBigDecimal("amount")
        else
          throw new Exception("Invalid RecordSet")
        rs.close()
        stmt.close()
        // 口座1の残高を減らす
        amount -= params.commute
        dao.executeUpdate("UPDATE transaction SET amount = " + amount.toString() + " WHERE id = 1;")
        // 口座2の残高読み込み
        query = "SELECT amount FROM transaction WHERE id = 2"
        if (params.for_update) {
          query += " FOR UPDATE;"
        } else {
          query += ";"
        }
        result = dao.executeQuery(query)
        stmt = result._1
        rs = result._2
        amount = BigDecimal(0)
        if (rs.next())
          amount = rs.getBigDecimal("amount")
        else
          throw new Exception("Invalid RecordSet")
        rs.close()
        stmt.close()
        // 口座2の残高を増やす
        amount += params.commute
        dao.executeUpdate("UPDATE transaction SET amount = " + amount.toString() + " WHERE id = 2;")
        transaction.commit
      }
    } catch {
      case e => println("Database error "+e)
      transaction.rollback
      connection.close()
      return
    }
    // 切断
    connection.close()
  }
}