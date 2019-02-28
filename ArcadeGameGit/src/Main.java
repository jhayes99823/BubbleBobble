import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

/**
 * The main class for your arcade game.
 * 
 * You can design your game any way you like, but make the game start by running
 * main here.
 * 
 * Also don't forget to write javadocs for your classes and functions!
 * 
 * @author Buffalo
 *
 */

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame frame = new JFrame();

		BubbleBobbleComponent bubComponent = new BubbleBobbleComponent(frame);
		
		FrameListener fl = new FrameListener(bubComponent, frame);
		frame.addKeyListener(fl);
		Timer centralTimer = new Timer(10, fl);
		JPanel extraStuffPanel = new JPanel();
		bubComponent.addPanels(extraStuffPanel);
		centralTimer.start();

		frame.add(extraStuffPanel, BorderLayout.NORTH);
		frame.setSize(905, 950);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Bubble Bobble Game");
		frame.add(bubComponent);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
