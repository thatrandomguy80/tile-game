package game.level;

import game.Screen;
import game.entities.Entity;
//import game.entities.Player;
import game.level.tiles.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
	public byte[] tiles;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	Random randomNum = new Random();
	private int Rresult = 0;

	public Level(int width, int height) {
		tiles = new byte[width * height];
		this.width = width;
		this.height = height;
		this.generateLevel();

	}

	public void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// stone boarders
				if (y == 0 || y == height || x == 0 || x == width) {
					tiles[x + y * width] = tile.STONE.getid();
				} else {//part of else so if i decide to make 1 say key random generate it won't be overtaken by the wall
					Rresult = randomNum.nextInt(9);
					if (Rresult == 1) {
						tiles[x + y * width] = tile.RED_MUSHROOM.getid();
					} else {
						tiles[x + y * width] = tile.GRASS.getid();
					}
				}
			}
		}
	}

	public void tick() {
		for (Entity e : entities) {
			e.tick();
		}
	}

	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > ((width << 3) - screen.width))
			xOffset = ((width << 3) - screen.width);
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > ((height << 3) - screen.height))
			yOffset = ((height << 3) - screen.height);

		screen.setOffset(xOffset, yOffset);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}

	public void renderEntites(Screen screen) {
		for (Entity e : entities) {
			e.render(screen);
		}
	}

	public tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height)
			return tile.VOID;
		return tile.tiles[tiles[x + y * width]];
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
}
