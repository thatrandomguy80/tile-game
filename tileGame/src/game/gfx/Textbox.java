package game.gfx;

import game.Screen;

public class Textbox {
	public int x, y;
	int height, width;
	int colour = Colours.get(-1, -1, -1, 000);
	Screen screen;

	public Textbox(int x, int y, Screen screen, int height, int width) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.screen = screen;
	}

	public void render(String text) {
		// draw opac box

		// font
		Font.render(text, screen, x, y, colour, 1);
	}
}
