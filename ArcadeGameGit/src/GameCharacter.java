import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class GameCharacter 
{
	protected Point pos;
	protected Image imageR;
	protected Image imageL;
	protected boolean facingRight;
	protected Point futurePos;
	protected double velocity;
	protected Point firstPositionEver;


	public GameCharacter() 
	{
		futurePos = new Point(0,0);
		facingRight = false;
		velocity = 10;
	}
	
	public void draw(Graphics2D g) 
	{
		if (facingRight)
		{
			g.drawImage(imageR,pos.x, pos.y, null);

		}
		else
		{
			g.drawImage(imageL, pos.x, pos.y, null);
		}
	}


	/**
	 * @param dx
	 * @param dy
	 * @param dir left or right
	 * 
	 * moves the character a certain amount in a direction
	 */
	public void move() 
	{
		if (pos.x < futurePos.x)
		{
			pos.x += velocity;
			
		}
		
		if (pos.x > futurePos.x)
		{
			pos.x -= velocity;
		}
		
		if (pos.y < futurePos.y)
		{
			pos.y += velocity;
		}
		
		if (pos.y > futurePos.y)
		{
			pos.y -= velocity;
		}
		
		
	}
	
	public void setFuturePos(int x, int y) 
	{
		if (x >= 0 && x <= 800)
		{
			futurePos.x = x;
		}
		
		if (y >= 0 && y <= 800)
		{
			futurePos.y = y;

		}
		
	}
	
	
	/**
	 * @param s string
	 * 
	 * sets the image for left and right of the character
	 */
	public void instantiateImage(String s) 
	{
		imageR = Toolkit.getDefaultToolkit().getImage(s+".png");
		try {

			imageR = ImageIO.read(new File(s+".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		imageL = Toolkit.getDefaultToolkit().getImage((s+"_opposite.png"));
		try {
			imageL = ImageIO.read(new File(s+"_opposite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	/**
	 * @param s
	 * 
	 * changes the image to character in bubble
	 */
	public void changeImageToInBubble(String s) {
//		imageR = Toolkit.getDefaultToolkit().getImage(s+".png");
//		try {
//			imageR = ImageIO.read(new File(s+".png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public void reset()
	{
		pos.x = firstPositionEver.x;
		pos.y = firstPositionEver.y;
		
		futurePos.x = firstPositionEver.x;
		futurePos.y = firstPositionEver.y;
		
	}
}
