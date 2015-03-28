package game;

//text box need much work

import game.entities.DevBrush;
import game.entities.Jones;
import game.entities.Player;
import game.gfx.Colours;
import game.gfx.Font;
import game.gfx.SpriteSheet;

import game.level.Level;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH =320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "Game";

	private JFrame frame;
	public boolean running = false;
	public int tickCount = 0;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int[] colours = new int[6 * 6 * 6];

	private Screen screen;
	public InputHandler input;
	public Level level;
	public static Player player;
	public Jones jones;
	public DevBrush dev;
	
	public int playerX=0;
	public int playerY=0;
	public static boolean devMode = false;
	
	int tempCount=0;

	public Game() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void init() {

		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);

					colours[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/SpriteSheet.png"));
		input = new InputHandler(this);
		level = new Level(64, 64, "res/level1.csv");
		player = new Player(level, 0, 0, input);
		jones = new Jones(level,"jones",0,0,0);
		dev = new DevBrush(level,0,0,input);
		
		level.addEntity(jones);
		level.addEntity(player);
		
		
	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();

	}

	public synchronized void stop() {

		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int frames = 0;
		int ticks = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		init();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender) {
				frames++;
				render();
			}
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println("frames:" + frames + "|| Ticks:" + ticks + "|| Shrooms:" + player.numOfShrooms);
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void tick() {
		tickCount++;
		level.tick();
		if(devMode){
			level.addEntity(dev);
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		int xOffset = player.x - (screen.width / 2);
		int yOffset = player.y - (screen.height / 2);
		
		// tiles
		level.renderTiles(screen, xOffset, yOffset);
		// font renders here

		String devtext = "DEVMODE ACTIVE";
		if(devMode){//prints above devBrush
			Font.render(devtext, screen, dev.x-((devtext.length()*8)/2-8),dev.y-17, Colours.get(000, -1, -1, -1), 1);
		}
		// sprites
		level.renderEntites(screen);

		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colourCode = screen.pixels[x + y * screen.width];
				if (colourCode < 255)
					pixels[x + y * WIDTH] = colours[colourCode];
			}
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
		g2d.drawString("test text",20, 20);
	}

	public static void main(String[] args) {
		new Game().start();
	}

}
