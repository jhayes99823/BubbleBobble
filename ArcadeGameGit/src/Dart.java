import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Dart implements GameObject
{

	protected Image image;
	protected Point pos;
	protected Point futurePos;
	boolean canDraw;
	protected boolean isDead;
	protected String name = "dart";
	
	public Dart(Point p) 
	{
		image = Toolkit.getDefaultToolkit().getImage(("dart.png"));
		try 
		{

			image = ImageIO.read(new File("dart.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		pos = p;
		futurePos = new Point(0,0);

		futurePos.x = pos.x;
		futurePos.y = pos.y - 100;
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
		if (pos.y >= futurePos.y)
		{
			pos.y -= 10;
			
		}
	}

	@Override
	public Point getPosition() 
	{
		return pos;
	}

	@Override
	public void onUpdate() 
	{
		if (this.pos.y != this.futurePos.y)
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
