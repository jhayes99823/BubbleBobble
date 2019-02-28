import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.Timer;

/**
 * @author hayesja1
 *
 */
/**
 * @author hayesja1
 *
 */
/**
 * @author hayesja1
 *
 */
public class Monster extends GameCharacter implements GameObject
{
	Scanner scan;
	int moveCounter = 0;
	Grid grid;
	public String name = "monster";

	int type;
	boolean isFloating;
	boolean isDead;
	
	public Monster(int type, Grid g) 
	{
		this.type = type;
		grid = g;
		super.instantiateImage("monster"+type);

		int r = (int) (Math.random() * (grid.getJumpableTiles().size()));
		pos = grid.getJumpableTiles().get(r).getPosition();
		
		firstPositionEver = new Point(0,0);

		firstPositionEver.x = pos.x;
		firstPositionEver.y = pos.y;
		
		futurePos.x = pos.x;
		futurePos.y = pos.y;
		
		facingRight = true;
		velocity = 5;
		isFloating = false;
		isDead = false;

	}
	




	/**
	 * gets the next position in the text file
	 */
	public void readNextPos() 
	{
		int random = (int) (Math.random() * 20) + 1;
//		int random = 2;
		if (random >= 1 && random < 7) // right
		{
			
			facingRight = true;
			if (grid.withinBounds(100,0, this))
			{
				if (grid.nextTileIsLandable(100, this))
				{
					this.setFuturePos(pos.x + 100,pos.y);
				}
				else 
				{
					Tile newTile = grid.findGroundTile(this);
					try{
						this.setFuturePos(newTile.x, newTile.y);
					}
					catch(NullPointerException e){}
				}
			}
			
		}
		if (random >= 7 && random < 12) //left 
		{

			facingRight = false;
			if (grid.withinBounds(-100,0, this))
			{

				if (grid.nextTileIsLandable(-100, this))
				{
					this.setFuturePos(pos.x-100, pos.y);
				}
				else
				{
					Tile newTile = grid.findGroundTile(this);
					try{
						this.setFuturePos(newTile.x, newTile.y);
					}
					catch(NullPointerException e){}				}
			}
		}
		if (random >= 12 && random < 15 && type != 1) // shoot bubble
		{

			Point point = new Point (this.pos.x, this.pos.y);
			grid.addBubble(point, this.facingRight, name);
		}

		
		if (random >= 15) 
		{
			int jumpTo = grid.gameObjectCanJumpTo(this);
			if ( jumpTo != 0)
			{
				this.setFuturePos(this.pos.x, this.pos.y - jumpTo);
			}
		}
		
	}



	/**
	 * @param x
	 * @param y
	 * 
	 * set position of monster
	 */
	public void setPosition(int x, int y) 
	{	
		pos.x = x;
		pos.y = y;
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public void onUpdate() 
	{
		if (this.pos.x == this.futurePos.x && this.pos.y == this.futurePos.y && !isFloating)
		{
			readNextPos();
		}
		move();
		
	}

	@Override
	public void die() 
	{
		isDead = true;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public String getName() 
	{
		return name;
	}

	public void hasCollidedWithBubble() 
	{
		this.instantiateImage("monster"+type+"_inBubble");
		this.futurePos.y = 0;
		isFloating = true;
	}
	
	public void hasCollidedWithDart()
	{
		this.die();
	}
	@Override
	public void reset()
	{
		if(!isFloating)
		{
			super.reset();
		}
	}
}
