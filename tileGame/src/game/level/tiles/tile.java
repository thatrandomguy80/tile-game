package game.level.tiles;

import game.Screen;
import game.gfx.Colours;
import game.level.Level;

public abstract class tile {
	//colours picker
	//path brown 542
	//red 460

	public static final tile[] tiles = new tile[256];
	public static final tile VOID = new BasicTile(0, 0, 0, Colours.get(000, -1, -1, -1));
	public static final tile STONE = new BasicSolidTile(1, 1, 0, Colours.get(-1, 333, -1, -1));
	public static final tile GRASS = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1));
	public static final tile BROWN_MUSHROOM = new BasicTile(3,3,0, Colours.get(555, 131, 141, 422));
	public static final tile RED_MUSHROOM = new BasicPickupTile(4,4,0, Colours.get(555, 131, 141, 460));
	public static final tile CHEST = new BasicSolidTile(5,6,0, Colours.get(000, 131, -1, 542));

	protected byte id;
	protected boolean solid;
	protected boolean pickup;
	protected boolean emitter;

	public tile(int id, boolean isSolid, boolean isPickup, boolean isEmitter) {
		this.id = (byte) id;
		if (tiles[id] != null)
			throw new RuntimeException("dupe tile id on " + id);
		this.solid = isSolid;
		this.pickup = isPickup;
		this.emitter = isEmitter;
		tiles[id] = this;
	}

	public byte getid() {
		return id;
	}
	
	public boolean isPickup(){
		return pickup;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isEmitter() {
		return emitter;
	}

	public abstract void render(Screen screen, Level level, int x, int y);

}
