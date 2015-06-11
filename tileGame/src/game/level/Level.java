package game.level;

import game.Screen;
import game.entities.Entity;
import game.gfx.Colours;
import game.gfx.Font;

//import game.entities.Player;
import game.level.tiles.tile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
	public byte[] tiles;
	public byte[] tiles1;// implemet 2nd layer for items in tile slots
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	Random randomNum = new Random();
	private int Rresult = 0;

	public Level(int width, int height, String levelpath) {
		tiles = new byte[width * height];
		tiles1 = new byte[width * height];
		this.width = width;
		this.height = height;
		this.loadLevel(levelpath);
		// this.generateLevel();

	}

	public void loadLevel(String filename) {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(filename));
			int y = 0;
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] temp = line.split(",");
				for (int x = 0; x < width; x++) {
					switch (temp[x]) {
					case "0":
						this.tiles[x + (y * this.width)] = tile.VOID.getid();
						break;
					case "1":
						this.tiles[x + (y * this.width)] = tile.STONE.getid();
						break;
					case "2":
						this.tiles[x + (y * this.width)] = tile.GRASS.getid();
						break;
					case "3":
						this.tiles[x + (y * this.width)] = tile.BROWN_MUSHROOM.getid();
						break;
					case "4":
						this.tiles[x + (y * this.width)] = tile.RED_MUSHROOM.getid();
						break;
					case "5":
						this.tiles[x + (y * this.width)] = tile.CHEST.getid();
						break;
					case "10":
						this.tiles[x + (y * this.width)] = tile.RD_UP.getid();
						break;
					case "11":
						this.tiles[x + (y * this.width)] = tile.RD_LEFT_DOWN.getid();
						break;
					case "12":
						this.tiles[x + (y * this.width)] = tile.RD_LEFT.getid();
						break;
					}
				}
				y++;
			}
			System.out.print("loaded\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveLevel() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("res/level1.csv", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int temp = tiles[x + (y * width)];
				System.out.print(temp);
				writer.print(temp);
				if (x != width - 1)
					writer.print(",");
			}
			writer.println();
		}
		writer.close();
		System.out.print("saved\n");
	}

	public void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// stone boarders
				if (y == 0 || y == height || x == 0 || x == width) {
					tiles[x + y * width] = tile.STONE.getid();
				} else {// part of else so if i decide to make 1 say key random generate it won't be overtaken by the wall
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

		for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {
			for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
				getTile(x, y, 0).render(screen, this, x << 3, y << 3);
			}
		}

	}

	public void renderEntites(Screen screen) {
		for (Entity e : entities) {
			e.render(screen);
		}
	}

	public void textBoxPrinting(String text, Screen screen, int xOffset, int yOffset) {
		int colour = Colours.get(555, -1, -1, 000);
		int colour2 = Colours.get(-1, -1, -1, 320);
		int charPerScreen = 40;
		int lines = text.length() / charPerScreen;
		for (int i = 0; i <= lines; i++) {
			if (i == lines) {
				Font.render(text.substring((40 * i), text.length()), screen, xOffset, yOffset + (i * 8), colour, 1);
				//boarder
				
				

				

			} else
				Font.render(text.substring((40 * i), ((40 * i)) + 40), screen, xOffset, yOffset + (i * 8), colour, 1);

		}
		
		screen.render(xOffset, yOffset, tile.LEFT.getid(), colour2, 0x00, 1);
		for (int j=1;j<text.length()-1;j++){
			screen.render(xOffset+(j*8), yOffset, tile.CENTER.getid(), colour2, 0x00, 1);
		}
		screen.render(xOffset+((text.length()*8) -8), yOffset, tile.RIGHT.getid(), Colours.get(-1, -1, -1, 420), 0x00, 1);
		
		
		// for (int i =0; text.length()<=charPerScreen*i; i++){
		// Font.render(text.substring(charPerScreen+i, (charPerScreen*(i+1))+i), screen, xOffset, yOffset, colour, 1);
		//
		// }
		// Font.render(text, screen, xOffset, yOffset, colour, 1);
		// Font.render(text, screen, xOffset, yOffset+8, colour, 1);
	}

	public tile getTile(int x, int y, int layer) {
		if (0 > x || x >= width || 0 > y || y >= height)
			return tile.VOID;
		switch (layer) {
		case 0:
			return tile.tiles[tiles[x + y * width]];
		case 1:
			return tile.tiles[tiles1[x + y * width]];
		default:
			return tile.tiles[tiles[x + y * width]];
		}
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
}
