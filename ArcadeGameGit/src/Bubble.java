import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Bubble implements GameObject
{
	protected Image image;
	protected Point pos;
	protected Point futurePos;
	boolean canDraw;
	protected boolean isDead;
	protected String name = "bubble";
	
	public Bubble(Point p, boolean facingRight, String s) {
		name = name + s;
		image = Toolkit.getDefaultToolkit().getImage(("bubble.png"));
		try 
		{

			image = ImageIO.read(new File("bubble.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		pos = p;
		futurePos = new Point(0,0);
		int i = 0;
		if (facingRight)
		{
			i = 300;
		}
		else
		{
			i = -300;
		}
		futurePos.x = pos.x + i;
		futurePos.y = pos.y;
		canDraw = true; 
		isDead = false;
	}

	public void draw(Graphics2D g) 
	{
		if (canDraw)
		{
			g.drawImage(image, pos.x, pos.y, null);
		}
	}

	/**
	 * moves the  bubble position
	 */
	public void move() 
	{
		if (pos.x < futurePos.x)
		{
			pos.x += 10;
			
		}
		
		if (pos.x > futurePos.x)
		{
			pos.x -= 10;
		}
	}

	@Override
	public Point getPosition() 
	{
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public void onUpdate() 
	{
		if (this.pos.x != this.futurePos.x)
		{
			move();
		}
		else
		{
			canDraw = false;
		}
	}

	@Override
	public void die() {
		isDead = true;
		this.pos.x = 1000;
		this.pos.y = 1000;
		futurePos.x = 1000;
		futurePos.y = 1000;
	}

	@Override
	public boolean isVisible() {
		return true;
	}
	@Override
	public String getName() 
	{
		return name;
	}

}
