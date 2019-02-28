import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author hayesja1
 *
 */
public class BubbleBobbleComponent extends JComponent {

	Graphics2D g2;
	Grid grid;
	JFrame f;

	static BufferedImage heartPic = null;

	static JLabel heartLabel1;
	static JLabel heartLabel2;
	static JLabel heartLabel3;

	static JLabel pointLabel;
	
	public BubbleBobbleComponent(JFrame f) {
		grid = new Grid(g2);
		this.f = f;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;

		grid.drawGrid(g2);

	}

	public void update(int t) {
		grid.updateGrid(g2);
		if (t % 1000 == 0 || t == 0) {
			grid.addFruit();
		}
		updatePanel();
		repaint();

	}

	private void updatePanel() 
	{
		if (grid.p.health == 3)
		{
			heartLabel1.setVisible(true);
			heartLabel2.setVisible(true);
			heartLabel3.setVisible(true);
		}
		else if (grid.p.health == 2) {
			
				heartLabel1.setVisible(true);
				heartLabel2.setVisible(true);
				heartLabel3.setVisible(false);
		}
		else if (grid.p.health == 1) {
			heartLabel1.setVisible(true);
			heartLabel2.setVisible(false);
			heartLabel3.setVisible(false);		}
		else {
			heartLabel3.setVisible(false);
			JOptionPane.showMessageDialog(null, "You Died");
			f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

		}
		
		pointLabel.setText("Points: " + grid.p.points);
		
	}

	public static void addPanels(JPanel extraStuffPanel) {
	

		try {
			heartPic = ImageIO.read(new File("heart.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		heartLabel1 = new JLabel(new ImageIcon(heartPic));
		heartLabel2 = new JLabel(new ImageIcon(heartPic));
		heartLabel3 = new JLabel(new ImageIcon(heartPic));
		pointLabel = new JLabel("Points:" + 0);

		// makes the label have a new font
		File f = null;
		f = new File("PressStart2P.ttf");

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, f);
		} catch (FontFormatException | IOException e) {
			System.out.println("your font is null");
		}

		font = font.deriveFont(Font.PLAIN, 30);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);

		pointLabel.setForeground(Color.red);
		pointLabel.setFont(font);

		// creates panel for extra stuff
		extraStuffPanel.setBackground(Color.black);
		heartLabel1.setBounds(0, 10, 100, 100);
		heartLabel2.setBounds(100, 10, 100, 100);
		heartLabel3.setBounds(200, 10, 100, 100);

		extraStuffPanel.add(heartLabel1);
		extraStuffPanel.add(heartLabel2);
		extraStuffPanel.add(heartLabel3);
		extraStuffPanel.add(pointLabel);
		
//		JButton help = new JButton("Help");
//		extraStuffPanel.add(help);
//		help.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) 
//			{
//				JOptionPane.showMessageDialog(null, "To go up a level, press U /n To go down a level, press D");
//
//				
//			}
//		});

	}

}
