package game.level.tiles;

import game.Screen;
import game.gfx.Colours;
import game.level.Level;

public abstract class tile {

	public static final tile[] tiles = new tile[256];
	public static final tile VOID = new BasicSolidTile(0, 0, 0, Colours.get(000, -1, -1, -1));
	public static final tile STONE = new BasicSolidTile(1, 1, 0, Colours.get(-1, 333, -1, -1));
	public static final tile GRASS = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1));

	protected byte id;
	protected boolean solid;
	protected boolean emitter;

	public tile(int id, boolean isSolid, boolean isEmitter) {
		this.id = (byte) id;
		if (tiles[id] != null)
			throw new RuntimeException("dupe tile id on " + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		tiles[id] = this;
	}

	public byte getid() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isEmitter() {
		return emitter;
	}

	public abstract void render(Screen screen, Level level, int x, int y);

}
