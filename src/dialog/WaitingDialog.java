package dialog;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class WaitingDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	
	public WaitingDialog (JFrame jFrame) {
		super(jFrame);
		JProgressBar bar = new JProgressBar();
		setUndecorated(true);
		bar.setIndeterminate(true);
		bar.setStringPainted(true);
		bar.setString("Please wait");
		setLocationRelativeTo(null);
		setPreferredSize(new Dimension(200, 50)); //it works
		jFrame.setEnabled(false);
		add(bar);
		pack();
	}

}
