package UI;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class WaitDialog extends JDialog { 
 private final boolean DEBUG = false; 

	private JPanel myPanel = null;
	private JProgressBar bar;
	
	public WaitDialog(String myMessage, int numBlocks) {
		super((JFrame) null, false);
		bar = new JProgressBar(0, numBlocks);
		myPanel = new JPanel();
		getContentPane().add(myPanel);
		myPanel.add(new JLabel(myMessage));
		myPanel.add(bar);
		pack();
		setLocationRelativeTo(null);
		validate();
		setVisible(true);
	}
	
	public void close(){
		this.setVisible(false);
	}
	
	public void setProgress(int progress){
		
	}

}