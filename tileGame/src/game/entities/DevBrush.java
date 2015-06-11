package game.entities;

import game.InputHandler;
import game.Screen;
import game.gfx.Colours;
import game.level.Level;
import game.level.tiles.tile;

public class DevBrush extends Mob {
	public int xTile = 0;
	public int yTile = 0;
	private InputHandler input;
	private int colour = Colours.get(100, 150, 125, 175);

	// create dev based 8 px object that moves 1 tile at a time and displays the current selected tile over the top of the mpa and when u press say f1 will print that tile to the level and when u press f10 will save f9 will load the prev save .
	public DevBrush(Level level, int x, int y, InputHandler input) {
		super(level, "devBrush", 4, 8, 8);
		this.input = input;
	}

	public boolean hasCollided(int xa, int ya) {
		return false;
	}

	public void tick() {
		int xa = 0;
		int ya = 0;

		if (input.f10.isPressed()) {
			level.saveLevel();
			input.f10.toggle(false);
		}
		if (input.f9.isPressed()) {
			level.loadLevel("res/level1.csv");
			input.f9.toggle(false);
		}
		if (input.devup.isPressed()) {
			ya--;
			input.devup.toggle(false);
		}
		if (input.devdown.isPressed()) {
			ya++;
			input.devdown.toggle(false);
		}
		if (input.devleft.isPressed()) {
			xa--;
			input.devleft.toggle(false);
		}
		if (input.devright.isPressed()) {
			xa++;
			input.devright.toggle(false);
		}

		if (input.f1.isPressed())
			this.xTile = 0;
		if (input.f2.isPressed())
			this.xTile = 1;
		if (input.f3.isPressed())
			this.xTile = 2;
		if (input.f4.isPressed())
			this.xTile = 3;
		if (input.f5.isPressed())
			this.xTile = 4;
		if (input.f6.isPressed())
			this.xTile = 5;
		if (input.f7.isPressed())
			this.xTile = 6;
		if (input.f8.isPressed())
			this.xTile = 7;

		if (input.space.isPressed()) {
			switch (xTile) {
			case 0:
				level.tiles[(this.x / 8) + (this.y / 8) * level.width] = tile.VOID.getid();
				break;
			case 1:
				level.tiles[(this.x / 8) + (this.y / 8) * level.width] = tile.STONE.getid();
				break;
			case 2:
				level.tiles[(this.x / 8) + (this.y / 8) * level.width] = tile.GRASS.getid();
				break;
			case 3:
				level.tiles[(this.x / 8) + (this.y / 8) * level.width] = tile.RED_MUSHROOM.getid();
				break;
			case 4:
				level.tiles[(this.x / 8) + (this.y / 8) * level.width] = tile.BROWN_MUSHROOM.getid();
				break;
			case 5:
				level.tiles[(this.x / 8) + (this.y / 8) * level.width] = tile.RD_UP.getid();
				break;
			case 6:
				level.tiles[(this.x / 8) + (this.y / 8) * level.width] = tile.RD_LEFT.getid();
				break;
			case 7:
				level.tiles[(this.x / 8) + (this.y / 8) * level.width] = tile.RD_LEFT_DOWN.getid();
				break;
			}
			//input.space.toggle(false);
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else
			isMoving = false;
	}

	public void render(Screen screen) {

		int modifier = 8;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;

		screen.render(xOffset + modifier, yOffset, xTile + yTile * 32, colour, 0, scale);

	}

}
