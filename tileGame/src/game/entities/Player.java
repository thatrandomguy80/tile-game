package game.entities;

import game.InputHandler;
import game.Screen;
import game.gfx.Colours;
import game.items.Item;
import game.level.Level;

import java.util.HashMap;
import java.util.Map;

public class Player extends Mob {

	private InputHandler input;
	private int colour = Colours.get(-1, 111, 145, 543);
	private int scale = 1;
	// collision box
	public int xMin = 0;
	public int xMax = 7 * scale;
	public int yMin = 3;
	public int yMax = 7 * scale;
	
	String username;

	public Map<Item, String> inven = new HashMap<Item, String>();
	public int numOfShrooms = 0;

	public Player(Level level, int x, int y, InputHandler input, String username) {
		super(level, "Player", 10, 10, 1);
		this.input = input;
		this.username = username;
	}

	public void tick() {
		movement();
	}

	public void movement() {
		int xa = 0;
		int ya = 0;
		if (input.up.isPressed())
			ya--;
		if (input.down.isPressed())
			ya++;
		if (input.left.isPressed())
			xa--;
		if (input.right.isPressed())
			xa++;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else
			isMoving = false;
		if (hasPickup(xa, ya)) {
			numOfShrooms += 1;
		}
	}

	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 28;
		// animation speed (lower is faster)
		int walkingSpeed = 3;
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;

		if (movingDir == 1) {
			xTile += 2;
		} else if (movingDir > 1) {
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
		}

		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;

		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop, scale);

		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour, flipBottom, scale);
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (1 + xTile) + (1 + yTile) * 32, colour, flipBottom, scale);
	}

	public boolean hasPickup(int xa, int ya) {
		for (int x = xMin; x < xMax; x++) {
			if (isPickupTile(xa, ya, x, yMin)) {
				return true;
			}
		}
		for (int x = xMin; x < xMax; x++) {
			if (isPickupTile(xa, ya, x, yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isPickupTile(xa, ya, xMin, y)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isPickupTile(xa, ya, xMax, y)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasCollided(int xa, int ya) {
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}
		return false;
	}

}
