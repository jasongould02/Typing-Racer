package runic.engine.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import runic.engine.util.math.MathUtil;

public class Keyboard implements KeyListener {

	//private boolean keys[] = new boolean[120];
	private boolean keys[] = new boolean[300];

	/*
	public boolean w, s, a, d;

	public void update() {
		w = keys[KeyEvent.VK_W];
		s = keys[KeyEvent.VK_S];
		a = keys[KeyEvent.VK_A];
		d = keys[KeyEvent.VK_D];
	}
	*/

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	public boolean isPressed(int code) {
		// if the button you press is a possible button
		if(MathUtil.inRange(code, keys.length, 0)) {
			return keys[code];
		} else {
			return false;
		}
	}

}
