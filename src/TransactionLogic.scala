trait TransactionLogic {
  def beginTran : Unit;
  def commit : Unit;
  def rollback : Unit;
}