package game.entities;

import java.util.Random;

import game.Screen;
import game.gfx.Colours;
import game.level.Level;

public class Jones extends Mob {
	private int colour = Colours.get(-1, 111, 145, 543);
	private int scale = 1;

	private int xMin = 0;
	private int xMax = 7;
	private int yMin = 3;
	private int yMax = 7;
	int targetX;
	int targetY;
	Random randomNum = new Random();
	int distFromTarget=8;
	int tempCount=0;

	public Jones(Level level, String name, int x, int y, int speed) {
		super(level, "Jones", 5, 10, 1);
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

	public void tick() {

		//adds delay to follow AI in ticks
		if (tempCount >=30){
			targetX = game.Game.player.x;
			targetY = game.Game.player.y;
			tempCount=0;
			}
			tempCount++;
		
		int xa = 0;
		int ya = 0;

		if (targetX + distFromTarget >= this.x)
			xa++;
		if (targetX - distFromTarget <= this.x)
			xa--;
		if (targetY + distFromTarget >= this.y)
			ya++;
		if (targetY - distFromTarget <= this.y)
			ya--;
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else
			isMoving = false;

	}

	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 28;

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

}
