package System;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
    
    // Track the screen X and Y coordinates of the mouse
    public int x = 0;
    public int y = 0;
    
    // Array to track which buttons are held down. 
    // Button 1 = Left Click, Button 2 = Middle Click, Button 3 = Right Click
    public boolean[] buttons = new boolean[4];

    // --- MOUSE MOTION LISTENER METHODS ---
    
    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Dragging happens when a button is held down while moving
        x = e.getX();
        y = e.getY();
    }

    // --- MOUSE LISTENER METHODS ---
    
    @Override
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        if (button < buttons.length) {
            buttons[button] = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        if (button < buttons.length) {
            buttons[button] = false;
        }
    }

    // These three are required by the interface, but we usually ignore them 
    // in game loops because press/release and movement cover everything we need.
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}