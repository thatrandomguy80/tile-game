package game.level.tiles;

public class BasicPickupTile extends BasicTile {

	public BasicPickupTile(int id, int x, int y, int tileColour) {
		super(id, x, y, tileColour);
		this.pickup = true;
	}
}
