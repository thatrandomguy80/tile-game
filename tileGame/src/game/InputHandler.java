package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import game.Game;

public class InputHandler implements KeyListener {

	public InputHandler(Game game) {
		game.addKeyListener(this);
	}

	public class Key {
		private int numTimesPressed = 0;
		private boolean pressed = false;

		public int getNumTimesPressed() {
			return numTimesPressed;
		}

		public boolean isPressed() {
			return pressed;
		}

		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if (isPressed)
				numTimesPressed++;
		}
	}

	public List<Key> keys = new ArrayList<Key>();
	// for movement
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	// dev movement
	public Key devup = new Key();
	public Key devdown = new Key();
	public Key devleft = new Key();
	public Key devright = new Key();
	
	//other keys
	public Key e = new Key();
	
	// for devmode
	public Key f1 = new Key();
	public Key f2 = new Key();
	public Key f3 = new Key();
	public Key f4 = new Key();
	public Key f5 = new Key();
	public Key f10 = new Key();
	public Key f9 = new Key();
	public Key space = new Key();

	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);

	}

	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);

	}

	public void keyTyped(KeyEvent e) {

	}

	public void toggleKey(int KeyCode, boolean isPressed) {
		if (KeyCode == KeyEvent.VK_W)
			up.toggle(isPressed);
		if (KeyCode == KeyEvent.VK_S)
			down.toggle(isPressed);
		if (KeyCode == KeyEvent.VK_A)
			left.toggle(isPressed);
		if (KeyCode == KeyEvent.VK_D)
			right.toggle(isPressed);

		if (KeyCode == KeyEvent.VK_UP) {
			devup.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_DOWN){
			devdown.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_LEFT){
			devleft.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_RIGHT){
			devright.toggle(isPressed);
		}

		
		if (KeyCode == KeyEvent.VK_SPACE)
			space.toggle(isPressed);
		if (KeyCode == KeyEvent.VK_F12)
			Game.devMode = true;
		if (KeyCode == KeyEvent.VK_F10)
			f10.toggle(isPressed);
		if (KeyCode == KeyEvent.VK_F9)
			f9.toggle(isPressed);

		if (KeyCode == KeyEvent.VK_F1)
			f1.toggle(isPressed);
		if (KeyCode == KeyEvent.VK_F2)
			f2.toggle(isPressed);
		if (KeyCode == KeyEvent.VK_F3)
			f3.toggle(isPressed);
		if (KeyCode == KeyEvent.VK_F4)
			f4.toggle(isPressed);
		if (KeyCode == KeyEvent.VK_F5)
			f5.toggle(isPressed);
		
		if (KeyCode == KeyEvent.VK_E)
			e.toggle(isPressed);
	}

}
