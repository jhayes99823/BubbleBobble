import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpPanel extends JPanel{
	
	private JLabel text;
	
	public HelpPanel() {
		text = new JLabel();
		text.setText("this is a test to see if it works. Will make prettier later.");
		add(text);
	}

}
