import java.sql.{DriverManager, Connection, Statement, ResultSet,SQLException}

// メインクラス
object Main {
  var log = ""
  
  def main(args : Array[String]) : Unit = {
    MainForm.main(args)
  }

  // TODO: ログをコンソールではなくGUIに表示
  def perform(params : Params) : Unit = {
    try {
      // 接続
      // FIXME: データベースの初期化ロジックを分離する
      val connection = DBFactory.getConnection(DB(params.database), params.isolation_level, params.table, params.user, params.password)
      val dao = DBFactory.getDAO(DB.MySQL, connection)
      val transaction = DBFactory.getTransactionLogic(DB.MySQL, params.transaction_type, connection)
      try {
        // データベース作成
        dao.executeUpdate("CREATE DATABASE IF NOT EXISTS trantest;")
        } catch {
          case e => println("Database error "+e)
          connection.close()
          return
        }
      try {
        // テーブル作成
        dao.executeUpdate("USE trantest;")
        dao.executeUpdate("DROP TABLE IF EXISTS transaction;")
        dao.executeUpdate("CREATE TABLE transaction (id INT(11) PRIMARY KEY, amount DECIMAL(13, 2)) ENGINE=InnoDB;")
        } catch {
          case e => println("Database error "+e)
          connection.close()
          return
        }
      try {
        // 初期データ投入
        transaction.beginTran
        dao.executeUpdate("INSERT INTO transaction (id, amount) VALUES(1, " + params.balance + ");")
        dao.executeUpdate("INSERT INTO transaction (id, amount) VALUES(2, 0);")
        transaction.commit
        } catch {
          case e => println("Database error "+e)
          transaction.rollback
          connection.close()
          return
        }
        // 切断
      connection.close()
      // テストロジックの実行
      // FIXME: テストのロジックを選択できるようにする
      var threads = List[Thread]()
      for (i <- 1 to params.thread_count) {
        val thread = new ExecuteThread(params)
        threads = thread :: threads
      }
      for (thread <- threads) {
        thread.start()
      }
      for (thread <- threads) {
        thread.join()
      }
    } catch {
      case e => println("Error "+e)
      return
    } finally {
    }
  }
}
