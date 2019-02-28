import java.awt.Point;
import java.util.ArrayList;

public class Player extends GameCharacter implements GameObject {

	protected final int MAX_TRAVEL_HEIGHT;
	protected int health;
	protected int points = 0;
	protected String name = "player";

	public Player() {
		super.instantiateImage("bubbleBobble_Player");
		facingRight = true;
		pos = new Point(0, 700);

		firstPositionEver = new Point(0, 0);

		firstPositionEver.x = pos.x;
		firstPositionEver.y = pos.y;

		futurePos.x = pos.x;
		futurePos.y = pos.y;
		MAX_TRAVEL_HEIGHT = 200;
		health = 3;
	}

	@Override
	public Point getPosition() {
		return pos;
	}

	@Override
	public void onUpdate() {
		move();
	}

	@Override
	public void die() {
	}

	@Override
	public boolean isVisible() {
		return true;
	}


	@Override
	public String getName() {
		return name;
	}

	public void collidedWithMonster() {
		health--;
		this.reset();
	}

	public void collidedWithFruit(GameObject g) 
	{

		this.points += ((Fruit) g).pointAmount;
		if (((Fruit)g).fileName.equals("heart.png"))
		{
			if (health!= 3)
			{
				health ++;
			}
		}
		g.die();
		

	}

	public void collidedWithMonsterBubble(GameObject g) {
		health--;
		this.reset();
		g.die();
	}
	

}
