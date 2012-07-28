import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.math.*;

public class MainForm {

	protected Shell shell;
	private Text txtLoopCount;
	private Text txtThreadCount;
	private Text txtBalance;
	private Text txtCommute;

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
		shell.setSize(450, 240);
		shell.setText("トランザクションテスト");
		
		final Button btnTransactionType = new Button(shell, SWT.CHECK);
		btnTransactionType.setBounds(10, 10, 128, 18);
		btnTransactionType.setText("トランザクション");
		
		final Combo cmbIsolationLevel = new Combo(shell, SWT.READ_ONLY);
		cmbIsolationLevel.setItems(new String[] {"READ COMMITTED", "REPEATABLE READ", "SERIALIZABLE"});
		cmbIsolationLevel.setBounds(96, 34, 134, 22);
		cmbIsolationLevel.select(0);
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(20, 38, 83, 14);
		label.setText("分離レベル");
		
		final Button btnForUpdate = new Button(shell, SWT.CHECK);
		btnForUpdate.setBounds(10, 62, 93, 18);
		btnForUpdate.setText("For Update");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(10, 90, 59, 14);
		label_1.setText("処理回数");
		
		txtLoopCount = new Text(shell, SWT.BORDER);
		txtLoopCount.setText("100");
		txtLoopCount.setBounds(74, 86, 64, 19);
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setText("スレッド数");
		label_2.setBounds(10, 114, 59, 14);
		
		txtThreadCount = new Text(shell, SWT.BORDER);
		txtThreadCount.setText("10");
		txtThreadCount.setBounds(74, 111, 64, 19);
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setText("残高");
		label_3.setBounds(10, 139, 59, 14);
		
		txtBalance = new Text(shell, SWT.BORDER);
		txtBalance.setText("10000");
		txtBalance.setBounds(74, 136, 64, 19);
		
		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setText("振替金額");
		label_4.setBounds(10, 162, 59, 14);
		
		txtCommute = new Text(shell, SWT.BORDER);
		txtCommute.setText("1");
		txtCommute.setBounds(74, 159, 64, 19);
		
		Button btnExecute = new Button(shell, SWT.NONE);
		btnExecute.addSelectionListener(new SelectionAdapter() {
			// 実行ボタン押されたぉ
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					boolean	transactionType = btnTransactionType.getSelection();
					int		isolationLevel = Integer.valueOf(cmbIsolationLevel.getSelectionIndex());
					boolean	forUpdate = btnForUpdate.getSelection();
					int		loopCount = Integer.valueOf(txtLoopCount.getText());
					int		threadCount = Integer.valueOf(txtThreadCount.getText());
					BigDecimal	balance = new BigDecimal(txtBalance.getText());
					BigDecimal	commute = new BigDecimal(txtCommute.getText());
					// Scalaのロジック呼び出します
					Main.perform(new Params(transactionType, isolationLevel, forUpdate,
							loopCount, threadCount, balance, commute));
				}
				catch (Exception exp) {
					MessageBox	messageBox = new MessageBox(shell, SWT.OK);
					messageBox.setMessage(exp.getMessage());
					messageBox.open();
				}
			}
		});
		btnExecute.setText("実行");
		btnExecute.setBounds(10, 182, 94, 28);

	}
}
