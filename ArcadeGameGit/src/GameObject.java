import java.awt.Graphics2D;
import java.awt.Point;

public interface GameObject 
{
	public String getName();
	
	public Point getPosition();
	
	public void onUpdate();
	
	public void die();
	
	public void draw(Graphics2D g2);
	
	public boolean isVisible();
}
