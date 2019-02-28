import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class Grid {

	protected Tile[][] tiles;
	private int levelCounter;
	File f;
	Scanner scan;

	// all the players in the game
	Player p;
	ArrayList<GameObject> monsters;
	ArrayList<GameObject> bubbles;
	ArrayList<GameObject> fruits;
	ArrayList<GameObject> darts;

	ArrayList<GameObject> allGameObjectsExceptPlayerAndItsBubble;
	Graphics2D graphics;

	public Grid(Graphics2D g2) {
		levelCounter = 1;
		graphics = g2;
		tiles = new Tile[9][9];
		instatiateGrid();
		readFile(levelCounter);

		monsters = new ArrayList<GameObject>();
		bubbles = new ArrayList<GameObject>();
		fruits = new ArrayList<GameObject>();
		darts = new ArrayList<GameObject>();

		allGameObjectsExceptPlayerAndItsBubble = new ArrayList<GameObject>();

		p = new Player();

		this.addMonsters();
	}

	/**
	 * reads level file
	 */
	protected void readFile(int level) {
		f = new File("level" + level + ".txt");
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("file does not exist");
		}

		try {
			scan.nextLine();
			scan.nextLine();
			while (scan.hasNextLine()) {
				String s = scan.nextLine();
				Integer x = Integer.parseInt(s.substring(0, 1));
				Integer y = Integer.parseInt(s.substring(2, 3));
				tiles[x][y].setGround(true);
				if (y != 0) {
					tiles[x][y - 1].setCanJump(true);
				}
			}
		} catch (NullPointerException n) {
			System.out.println("file null because file not found");
		}

		scan.close();
	}

	/**
	 * makes the initial grid
	 */
	private void instatiateGrid() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Tile t = new Tile(i * 100, j * 100);
				tiles[i][j] = t;
			}
		}

	}

	public void draw(Graphics2D g2) {
		for (Tile[] i : tiles) {
			for (Tile j : i) {
				if (j.c.equals(Color.BLACK)) {
					g2.setColor(j.c);
					g2.fillRect(j.x, j.y, 100, 100);
				}

				else {
					Image image = Toolkit.getDefaultToolkit().getImage("tile.png");
					try {

						image = ImageIO.read(new File("tile.png"));

					} catch (IOException e) {
						e.printStackTrace();
					}

					g2.drawImage(image, j.x, j.y, null);

				}

//				g2.setColor(j.c);
//				g2.fillRect(j.x, j.y, 100, 100);

			}
		}

	}

	/**
	 * @param x coordinate
	 * @param y coordinate
	 * @return the tile of the provided x and y
	 */
	public Tile getTileByLoc(int x, int y) {

		for (Tile[] i : tiles) {
			for (Tile j : i) {
				if (x == j.x && y == j.y) {
					return j;
				}
			}
		}
		return null;
	}

	/**
	 * @param i level number
	 * @return whether or not the file exists to read
	 */
	public boolean levelFileExists(int i) {
		File temp = new File("level" + i + ".txt");
		try {
			Scanner tempScanner = new Scanner(temp);
		} catch (FileNotFoundException e) {
			if (i > this.levelCounter) {
				JOptionPane.showMessageDialog(null, "The level above does not exist.");
			} else {
				JOptionPane.showMessageDialog(null, "The level below does not exist.");
			}
			return false;
		}

		return true;
	}

	/**
	 * @return list of all the ground tiles for the grid
	 */
	public ArrayList<Tile> getGroundTiles() {
		ArrayList<Tile> returnList = new ArrayList<>();

		for (int i = 0; i < tiles.length; i++) {
			for (int k = 0; k < tiles[0].length; k++) {
				if (tiles[i][k].hasGround == true) {
					returnList.add(tiles[i][k]);
				}
			}
		}

		return returnList;

	}

	/**
	 * @return list of all the jumpable tiles for the grid
	 */
	public ArrayList<Tile> getJumpableTiles() {
		ArrayList<Tile> returnList = new ArrayList<>();

		for (int i = 0; i < tiles.length; i++) {
			for (int k = 0; k < tiles[0].length; k++) {
				if (tiles[i][k].canLand == true) {
					returnList.add(tiles[i][k]);
				}
			}
		}

		return returnList;
	}

	public void updateGrid(Graphics2D g2) {
		allGameObjectsExceptPlayerAndItsBubble.clear();
		addToBigArray();
		p.onUpdate();

		for (GameObject m : monsters) {
			m = (Monster) m;

			m.onUpdate();
		}

		for (GameObject b : bubbles) {
			b = (Bubble) b;

			b.onUpdate();
		}

		for (GameObject d : darts) {
			d = (Dart) d;

			d.onUpdate();
		}

		checkCollisions();
		killDeadStuff();
	}

	private void killDeadStuff() {
		ArrayList<Bubble> killThese = new ArrayList<>();
		ArrayList<Fruit> killTheseToo = new ArrayList<>();
		ArrayList<Monster> killTheseThree = new ArrayList<>();
		ArrayList<Dart> killTheseFour = new ArrayList<>();
		for (GameObject b : bubbles) {
			Bubble bub = ((Bubble) b);

			if (bub.isDead) {
				killThese.add(bub);
			}
		}

		for (GameObject f : fruits) {
			Fruit fruit = ((Fruit) f);

			if (fruit.isDead) {
				killTheseToo.add(fruit);
			}
		}

		for (GameObject m : monsters) {
			Monster mon = ((Monster) m);

			if (mon.isDead) {
				killTheseThree.add(mon);
			}
		}

		for (GameObject d : darts) {
			Dart da = ((Dart) d);

			if (da.isDead) {
				killTheseFour.add(da);
			}
		}

		bubbles.removeAll(killThese);
		fruits.removeAll(killTheseToo);
		monsters.removeAll(killTheseThree);
		darts.removeAll(killTheseFour);

	}

	private void addToBigArray() {
		for (GameObject m : monsters) {
			allGameObjectsExceptPlayerAndItsBubble.add(m);
		}

		for (GameObject f : fruits) {
			allGameObjectsExceptPlayerAndItsBubble.add(f);
		}

		for (GameObject b : bubbles) {
			Bubble bub = (Bubble) b;
			if (bub.name.equals("bubblemonster")) {
				allGameObjectsExceptPlayerAndItsBubble.add(b);
			}
		}
	}

	private void checkCollisions() {
		for (GameObject gameObj : allGameObjectsExceptPlayerAndItsBubble) {
			if (p.pos.x > gameObj.getPosition().x - 20 && p.pos.x < gameObj.getPosition().x + 20
					&& p.pos.y > gameObj.getPosition().y - 20 && p.pos.y < gameObj.getPosition().y + 20) {

				if (gameObj.getName().equals("monster")) {

					p.collidedWithMonster();
					monsterReset();
				}
				if (gameObj.getName().equals("fruit")) {
					p.collidedWithFruit(gameObj);
				}
				if (gameObj.getName().equals("bubblemonster")) {
					p.collidedWithMonsterBubble(gameObj);
					monsterReset();
				}
			}
		}

		ArrayList<Bubble> tempBubbs = new ArrayList<>();

		for (GameObject obj : bubbles) {
			if (obj.getName().equals("bubbleplayer")) {
				tempBubbs.add((Bubble) obj);
			}
		}

		for (GameObject gameObj : monsters) {
			for (GameObject gameBub : tempBubbs) {
				Monster temp = (Monster) gameObj;
				if (temp.pos.x > gameBub.getPosition().x - 20 && temp.pos.x < gameBub.getPosition().x + 20
						&& temp.pos.y > gameBub.getPosition().y - 20 && temp.pos.y < gameBub.getPosition().y + 20) {
					temp.hasCollidedWithBubble();
					gameBub.die();
				}
			}
			for (GameObject g : darts) {
				Monster temp = (Monster) gameObj;
				if (temp.pos.y >= g.getPosition().y && temp.pos.y <= g.getPosition().y + 75
						&& temp.pos.x <= g.getPosition().x + 100 && temp.pos.x >= g.getPosition().x - 100) {
					temp.hasCollidedWithDart();
					Point tempPt = new Point(temp.pos.x, temp.pos.y + 100);
					addFruitAtLoc(tempPt);
				}

			}
		}

	}

	private void monsterReset()

	{
		for (GameObject g : monsters) {
			((Monster) g).reset();
		}

	}

	/**
	 * @return new grid based on increased level
	 */
	public void increaseLevel() {
		fruits.clear();
		bubbles.clear();
		darts.clear();
		monsters.clear();
		p.reset();
		if (this.levelFileExists(levelCounter + 1)) {
			levelCounter++;
			instatiateGrid();
			readFile(levelCounter);
		}
		addMonsters();

	}

	private void addMonsters() {
		Monster m1 = new Monster(1, this);
		monsters.add(m1);

		if (this.levelCounter == 2) {
			Monster m2 = new Monster(2, this);
			monsters.add(m2);
		}

		if (this.levelCounter == 3) {
			Monster m2 = new Monster(2, this);
			monsters.add(m2);
			Monster m4 = new Monster(1, this);
			monsters.add(m4);
		}

	}

	/**
	 * @return new grid based on decreased level
	 */
	public void decreaseLevel() {
		fruits.clear();
		bubbles.clear();
		darts.clear();
		monsters.clear();
		p.reset();
		if (this.levelFileExists(levelCounter - 1)) {
			levelCounter--;
			instatiateGrid();
			readFile(levelCounter);
		}
		addMonsters();

	}

	public void drawGrid(Graphics2D g2) {
		this.draw(g2);
		p.draw(g2);

		for (GameObject go : monsters) {
			Monster m = (Monster) go;
			m.draw(g2);
		}
		for (GameObject go : bubbles) {
			Bubble b = (Bubble) go;
			b.draw(g2);
		}
		for (GameObject go : fruits) {
			Fruit f = (Fruit) go;
			f.draw(g2);
		}
		for (GameObject go : darts) {
			Dart d = (Dart) go;
			d.draw(g2);
		}
	}

	public int gameObjectCanJumpTo(GameCharacter g) {
		Tile aboveCurrent = this.getTileByLoc(g.pos.x, g.pos.y - 200);
		Tile twoAboveCurrent = this.getTileByLoc(g.pos.x, g.pos.y - 300);

		if (aboveCurrent != null && aboveCurrent.canLand) {
			return 200;
		}

		if (twoAboveCurrent != null && twoAboveCurrent.canLand) {
			return 300;
		}

		return 0;
	}

	public boolean nextTileIsLandable(int i, GameCharacter g) {
		Tile nextTile = this.getTileByLoc(g.pos.x + i, g.pos.y);
		if (nextTile != null && nextTile.canLand) {
			return true;
		}

		return false;
	}

	public int fallTile() {

		return 0;
	}

	public Tile findGroundTile(GameCharacter g) {

		int i = 0;
		if (g.facingRight) {
			i = 100;
		} else {
			i = -100;
		}
		for (int y = g.pos.y / 100; y < 8; y++) {
			Tile newTile = this.getTileByLoc(g.pos.x + i, y * 100);
			if (newTile != null && newTile.canLand) {
				return newTile;
			}
		}

		return this.getTileByLoc(g.pos.x, g.pos.y);

	}

	public boolean withinBounds(int i, int j, GameCharacter g) {
		if (g.pos.x + i <= 800 || g.pos.x + i >= 0) {
			return true;
		}
		return false;
	}

	public void addBubble(Point position, boolean facingRight, String s) {
		Bubble b = new Bubble(position, facingRight, s);
		bubbles.add(b);

	}

	public void addDart(Point position) {
		Dart d = new Dart(position);
		darts.add(d);

	}

	public void addFruit() {
		int random = (int) (Math.random() * (this.getJumpableTiles().size()));

		Tile rand = this.getJumpableTiles().get(random);

		Point r = new Point(rand.x, rand.y);
		Fruit f = new Fruit(r);

		fruits.add(f);

	}

	public void addFruitAtLoc(Point pt) {
		Point r = pt;
		Fruit f = new Fruit(r);

		fruits.add(f);
	}

}
