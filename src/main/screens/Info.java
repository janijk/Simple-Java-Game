package main.screens;

import main.Game;
import main.enums.State;
import main.util.FileWriter;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Info extends KeyAdapter {
    private int highScore, timeout;

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        // Esc key
        if (key == 27) Game.state = State.MENU;
    }

    public void tick(){
        if (Game.state == State.INFO && timeout-- <= 0){
            try {
                highScore = FileWriter.readInt();
            }catch (IOException e){
                timeout = 1000;
                e.printStackTrace();
            }
        }
    }

    public void render(Graphics graphics){
        graphics.setColor(Color.lightGray);

        graphics.setFont(new Font("arial", 1, 30));
        graphics.drawString("Tips", Game.WIDTH/2-100,Game.HEIGHT/7);

        graphics.setFont(new Font("arial", 1, 20));
        graphics.drawString("Avoid being hit by enemies", Game.WIDTH/2-80,Game.HEIGHT/5+30);
        graphics.drawString("Use arrow keys to move", Game.WIDTH/2-80,Game.HEIGHT/5+60);
        graphics.drawString("Boss fight every 20th level", Game.WIDTH/2-80,Game.HEIGHT/5+90);


        graphics.setFont(new Font("arial", 1, 30));
        graphics.drawString("High score:", Game.WIDTH/2-100,Game.HEIGHT/2+30);

        graphics.setFont(new Font("arial", 1, 20));
        graphics.drawString("Level: " + highScore, Game.WIDTH/2-80,Game.HEIGHT/2+80);
        graphics.drawString("Hit Esc to go back", Game.WIDTH/2-80,Game.HEIGHT-100);
    }
}
