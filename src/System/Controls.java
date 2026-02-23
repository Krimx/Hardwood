package System;

import java.awt.event.KeyEvent;
import com.studiohartman.jamepad.ControllerState;

public class Controls {
    // Keyboard Mappings
    public int KEY_LEFT = KeyEvent.VK_A;
    public int KEY_RIGHT = KeyEvent.VK_D;
    public int KEY_JUMP = KeyEvent.VK_SPACE;
    public int KEY_CROUCH = KeyEvent.VK_SHIFT;
    public int KEY_ESCAPE = KeyEvent.VK_ESCAPE;
    
    public boolean jumpable = false;
    private float deadzone = 0.2f;

    // These methods check BOTH keyboard and controller
    public boolean moveLeft(KeyHandler keys, ControllerState curr) {
        return keys.keys[KEY_LEFT] || curr.dpadLeft || curr.leftStickX < -deadzone;
    }

    public boolean moveRight(KeyHandler keys, ControllerState curr) {
        return keys.keys[KEY_RIGHT] || curr.dpadRight || curr.leftStickX > deadzone;
    }

    public boolean jump(KeyHandler keys, ControllerState curr) {
        return keys.keys[KEY_JUMP] || curr.a || curr.dpadUp;
    }
    
    public boolean crouch(KeyHandler keys, ControllerState curr) {
    	return keys.keys[KEY_CROUCH] || curr.b || curr.dpadDown;
    }
    
    public boolean escape(KeyHandler keys, ControllerState curr) {
    	return keys.isJustPressed(KEY_ESCAPE) || curr.startJustPressed;
    }
    
    public boolean debug(KeyHandler keys, ControllerState curr) {
    	return curr.backJustPressed;
    }
}