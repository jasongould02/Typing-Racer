package runic.engine.util;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import runic.engine.util.math.MathUtil;

/**
 * Created on 29/12/2015.
 */
public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener, MouseInputListener {

	private boolean[] buttons = new boolean[20];
	private int xpos = 0;
	private int ypos = 0;
	private int wheelRotation = 0;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	System.out.println("MouseEvent: " + e.getX() + "," + e.getY() + "MouseInfo: " + MouseInfo.getPointerInfo().getLocation().getX() + "," + MouseInfo.getPointerInfo().getLocation().getY());
    	buttons[e.getButton()] = true;
    	xpos = (int) e.getX();
    	ypos = (int) e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
    	xpos = (int) e.getX();
    	ypos = (int) e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    	xpos = (int) e.getX();
    	ypos = (int) e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    	wheelRotation = e.getWheelRotation();
    }
    
    public boolean isPressed(int button) {
    	if(MathUtil.inRange(button, buttons.length, 0)) {
			return buttons[button];
		} else {
			return false;
		}
    }
    
    public int getX() {
    	return xpos;
    }

    public int getY() {
    	return ypos;
    }
    
    public int getWheelRotation() {
    	return wheelRotation;
    }
    
}
