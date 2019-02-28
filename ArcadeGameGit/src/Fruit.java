import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Fruit implements GameObject
{
	protected Image image;
	protected Point pos;
	protected int pointAmount;
	protected boolean isDead;
	protected String fileName;
	public String name = "fruit";

	// 1 / 15 = diamond;   * worth 15 
	// 2 / 15 = cake       * worth 7
	// 3 / 15 = cupcake    * worth 5
	// 4 / 15 = watermelon * worth 4
	// 5 / 15 = orange     * worth 3
	private String[] names= {"cake","cake","cupcake","cupcake","cupcake","watermelon","watermelon","watermelon","watermelon","orange","orange","orange","orange","orange","diamond","heart"};
	private int[] amounts = {7,7,5,5,5,4,4,4,4,3,3,3,3,3,15,0};
	
	public Fruit(Point p) 
	{
		newFruitType();
		pos = p;
		isDead = false;
	}

	public void draw(Graphics2D g) 
	{
		g.drawImage(image, pos.x, pos.y, null);
	}

	/**
	 * picks a random fruit and instantiates a point value for it
	 */
	public void newFruitType() 
	{
		Integer random = (int) (Math.random() * (16));
//		Integer random = 15;
		fileName = names[random] + ".png";
		image = Toolkit.getDefaultToolkit().getImage(fileName);
		try 
		{

			image = ImageIO.read(new File(fileName));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		pointAmount = amounts[random];
		
	}
	
	@Override
	public Point getPosition() 
	{
		return pos;
	}

	@Override
	public void onUpdate() 
	{}

	@Override
	public void die() 
	{
		isDead = true;
		this.pos.x = 1000;
		this.pos.y = 1000;

	}

	@Override
	public boolean isVisible() 
	{
		return true;
	}

	@Override
	public String getName() 
	{
		return name;
	}

}
