import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class FrameListener implements KeyListener, ActionListener
{
	BubbleBobbleComponent bubComponent;
	JFrame frame;
	int hearts = 3;
	Player p;
	Grid grid;
	int t = 0;

	
	public FrameListener(BubbleBobbleComponent bubComponent, JFrame frame) 
	{
		this.bubComponent = bubComponent;
		this.frame = frame;
		p = (Player) bubComponent.grid.p;
		grid = bubComponent.grid;
	}

	public void keyPressed(KeyEvent arg0) 
	{
		if (arg0.getKeyCode() == KeyEvent.VK_UP) 
		{
			int jumpTo = grid.gameObjectCanJumpTo(p);
			if ( jumpTo != 0)
			{
				p.setFuturePos(p.pos.x, p.pos.y - jumpTo);
			}

		}
	
//		// key listener for left movement
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) //IF THEY ARE GOING LEFT
		{
  			p.facingRight = false;
			if (grid.withinBounds(-100,0,p))
			{
				if (grid.nextTileIsLandable(-100,p))
				{
					p.setFuturePos(p.pos.x-100, p.pos.y);
				}
				else
				{
					Tile newTile = grid.findGroundTile(p);
					try{
						p.setFuturePos(newTile.x, newTile.y);
					}
					catch(NullPointerException e){}
					
				}
			}
		}

//		// key listener for right movement
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) 
		{
			p.facingRight = true;
			if (grid.withinBounds(100,0,p))
			{
				if (grid.nextTileIsLandable(100, p))
				{
					p.setFuturePos(p.pos.x + 100, p.pos.y);
				}
				else
				{
					Tile newTile = grid.findGroundTile(p);
					try{
						p.setFuturePos(newTile.x, newTile.y);
					}
					catch(NullPointerException e){}

				}
			}
				

		}

		if (arg0.getKeyCode() == KeyEvent.VK_A) 
		{
			Point point = new Point(p.pos.x+50, p.pos.y);
			grid.addDart(point);
		}

		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) 
		{
			Point point = new Point (p.pos.x, p.pos.y);
			grid.addBubble(point, p.facingRight, p.name);
		}
		
		if (arg0.getKeyCode() == KeyEvent.VK_U) 
		{
			grid.increaseLevel();
		}
		
		if (arg0.getKeyCode() == KeyEvent.VK_D) 
		{
			grid.decreaseLevel();
		}
	
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		bubComponent.update(t);
		t ++;
	}

}
