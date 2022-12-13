package main.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    public static boolean[] keys;

    public KeyInput() {
        keys = new boolean[4];
    }

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        // Left - Up - Right - Down arrows
        if (key == 37) keys[0] = true;
        if (key == 38) keys[1] = true;
        if (key == 39) keys[2] = true;
        if (key == 40) keys[3] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // Left - Up - Right - Down arrows
        if (key == 37) keys[0] = false;
        if (key == 38) keys[1] = false;
        if (key == 39) keys[2] = false;
        if (key == 40) keys[3] = false;
    }
}
