import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.math.*;

// メイン画面(WindowBuilderで自動生成)
public class MainForm {

  protected Shell shell;
  private Text txtLoopCount;
  private Text txtThreadCount;
  private Text txtBalance;
  private Text txtCommute;
  private Text txtHost;
  private Text txtUser;
  private Text txtPassword;
  public Text txtLog;

  /**
   * Launch the application.
   * @param args
   */
  public static void main(String[] args) {
    try {
      MainForm window = new MainForm();
      window.open();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Open the window.
   */
  public void open() {
    Display display = Display.getDefault();
    createContents();
    shell.open();
    shell.layout();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * Create contents of the window.
   */
  protected void createContents() {
    shell = new Shell();
    shell.setSize(450, 559);
    shell.setText("トランザクションテスト");

    Group group = new Group(shell, SWT.NONE);
    group.setText("データベース");
    group.setBounds(10, 10, 430, 83);

    final Combo cmbDatabase = new Combo(group, SWT.READ_ONLY);
    cmbDatabase.setItems(new String[] {"MySQL"});
    cmbDatabase.setBounds(86, 10, 178, 22);
    cmbDatabase.select(0);
    
    Label label_7 = new Label(group, SWT.NONE);
    label_7.setText("データベース");
    label_7.setBounds(10, 14, 83, 14);

    Label lblHost = new Label(group, SWT.NONE);
    lblHost.setBounds(10, 40, 45, 14);
    lblHost.setText("マシン");

    txtHost = new Text(group, SWT.BORDER);
    txtHost.setText("localhost");
    txtHost.setBounds(58, 35, 91, 19);

    Label label_5 = new Label(group, SWT.NONE);
    label_5.setText("ユーザ");
    label_5.setBounds(155, 40, 45, 14);

    txtUser = new Text(group, SWT.BORDER);
    txtUser.setText("root");
    txtUser.setBounds(203, 35, 67, 19);

    Label label_6 = new Label(group, SWT.NONE);
    label_6.setText("パスワード");
    label_6.setBounds(279, 40, 64, 14);

    txtPassword = new Text(group, SWT.BORDER);
    txtPassword.setBounds(349, 37, 67, 19);

    final Button btnTransactionType = new Button(shell, SWT.CHECK);
    btnTransactionType.setBounds(10, 99, 128, 18);
    btnTransactionType.setText("トランザクション");

    final Combo cmbIsolationLevel = new Combo(shell, SWT.READ_ONLY);
    cmbIsolationLevel.setItems(new String[] {"READ-UNCOMMITTED", "READ-COMMITTED", "REPEATABLE-READ", "SERIALIZABLE"});
    cmbIsolationLevel.setBounds(86, 123, 178, 22);
    cmbIsolationLevel.select(0);

    Label label = new Label(shell, SWT.NONE);
    label.setBounds(10, 127, 83, 14);
    label.setText("分離レベル");

    final Button btnForUpdate = new Button(shell, SWT.CHECK);
    btnForUpdate.setBounds(10, 151, 93, 18);
    btnForUpdate.setText("For Update");

    Label label_1 = new Label(shell, SWT.NONE);
    label_1.setBounds(10, 179, 59, 14);
    label_1.setText("処理回数");

    txtLoopCount = new Text(shell, SWT.BORDER);
    txtLoopCount.setText("100");
    txtLoopCount.setBounds(74, 175, 64, 19);

    Label label_2 = new Label(shell, SWT.NONE);
    label_2.setText("スレッド数");
    label_2.setBounds(10, 203, 59, 14);

    txtThreadCount = new Text(shell, SWT.BORDER);
    txtThreadCount.setText("10");
    txtThreadCount.setBounds(74, 200, 64, 19);

    Label label_3 = new Label(shell, SWT.NONE);
    label_3.setText("残高");
    label_3.setBounds(10, 228, 59, 14);

    txtBalance = new Text(shell, SWT.BORDER);
    txtBalance.setText("10000");
    txtBalance.setBounds(74, 225, 64, 19);

    Label label_4 = new Label(shell, SWT.NONE);
    label_4.setText("振替金額");
    label_4.setBounds(10, 251, 59, 14);

    txtCommute = new Text(shell, SWT.BORDER);
    txtCommute.setText("10");
    txtCommute.setBounds(74, 248, 64, 19);
    
    Button btnExecute = new Button(shell, SWT.NONE);
    btnExecute.addSelectionListener(new SelectionAdapter() {
      // 実行ボタン押されたぉ
      @Override
      public void widgetSelected(SelectionEvent e) {
        try {
          int database = Integer.valueOf(cmbDatabase.getSelectionIndex());
          boolean  transactionType = btnTransactionType.getSelection();
          int    isolationLevel = Integer.valueOf(cmbIsolationLevel.getSelectionIndex());
          boolean  forUpdate = btnForUpdate.getSelection();
          int    loopCount = Integer.valueOf(txtLoopCount.getText());
          int    threadCount = Integer.valueOf(txtThreadCount.getText());
          BigDecimal  balance = new BigDecimal(txtBalance.getText());
          BigDecimal  commute = new BigDecimal(txtCommute.getText());
          // Scalaのロジック呼び出します
          Main.perform(new Params(database, txtHost.getText(), "", txtUser.getText(), txtPassword.getText(),
              transactionType, isolationLevel, forUpdate,
              loopCount, threadCount, balance, commute));
        }
        catch (Exception exp) {
          MessageBox  messageBox = new MessageBox(shell, SWT.OK);
          messageBox.setMessage(exp.getMessage());
          messageBox.open();
        }
      }
    });
    btnExecute.setText("実行");
    btnExecute.setBounds(10, 271, 94, 28);
    
    txtLog = new Text(shell, SWT.BORDER);
    txtLog.setEditable(false);
    txtLog.setBounds(10, 305, 430, 222);
  }
}
