package System;

import java.awt.event.KeyEvent;
import com.studiohartman.jamepad.ControllerState;

public class Controls {
    // Keyboard Mappings
    public int KEY_LEFT = KeyEvent.VK_A;
    public int KEY_RIGHT = KeyEvent.VK_D;
    public int KEY_JUMP = KeyEvent.VK_SPACE;
    
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
        return keys.keys[KEY_JUMP] || curr.a;
    }
}