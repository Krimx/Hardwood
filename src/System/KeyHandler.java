package System;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    // Array to track state of all 256 basic keys
    public boolean[] keys = new boolean[256];
    private boolean[] lastKeys = new boolean[256]; // Track previous frame

    public void update() {
        // Copy current keys to lastKeys at the end of every tick
        System.arraycopy(keys, 0, lastKeys, 0, keys.length);
    }

    public boolean isJustPressed(int keyCode) {
        // True only if it's down NOW, but was NOT down in the last frame
        return keys[keyCode] && !lastKeys[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code < keys.length) keys[code] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code < keys.length) keys[code] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used for game movement usually
    }
}