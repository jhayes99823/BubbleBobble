import java.awt.Color;
import java.awt.Point;


public class Tile {

	protected int x, y;
	protected boolean hasGround;
	protected boolean canLand;
	protected boolean hasFruit;
	protected Color c;
	
	public Tile(int x, int y) 
	{
		this.x = x;
		this.y = y;
		this.hasGround = false;
		this.canLand = false;
		this.hasFruit = false;
		c = Color.BLACK;
		
	}
	
	/**
	 * @param b true if ground false otherwise
	 * makes a tile a ground tile
	 */
	public void setGround(boolean b)
	{
		hasGround = b;
		setColor();
	}

	/**
	 * sets the color to purple if it is ground
	 */
	public void setColor() {
		if(hasGround == true)
		{
			c = Color.red;
		}
		
		
		
	}

	
	@Override
	public String toString() {
		return  " hasGround: " + hasGround + " x: " + x + " y: " + y;
	}
	
	/**
	 * @param b true if a jumpable tile false otherwise
	 */
	public void setCanJump(boolean b)
	{
		canLand = b;
		setColor();
	}
	
	public Point getPosition()
	{
		Point newP = new Point(this.x,this.y);
		return newP;
	}
}
