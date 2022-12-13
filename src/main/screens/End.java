package main.screens;

import main.Game;
import main.StatusBar;
import main.enums.ID;
import main.enums.STATE;
import main.objects.Player;
import main.util.Handler;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class End extends MouseAdapter {
    private StatusBar statusBar;
    private Handler handler;

    public End(StatusBar statusBar, Handler handler) {
        this.statusBar = statusBar;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX(), my = e.getY();
        // Restart box click
        if (mouseAt(mx,my, Game.WIDTH/2-65,Game.HEIGHT/2,150,60)){
            handler.removeAllGameObjects();
            handler.addGameObject(new Player(Game.WIDTH/2, Game.HEIGHT/2, ID.Player, handler, statusBar));
            statusBar.reset();
            Game.state = STATE.GAME;
        }
    }

    // Return true/false whether mouse click is within parameter boundaries
    private boolean mouseAt(int mx, int my, int x, int y, int w, int h){
        if (mx > x && mx < (x+w) && my > y && my < (y+h)){
            return true;
        }
        return false;
    }

    public void render(Graphics graphics){
        graphics.setColor(new Color(0,0,0,200));
        graphics.fillRect(0,0,Game.WIDTH,Game.HEIGHT);

        graphics.setColor(Color.lightGray);
        graphics.drawRect(Game.WIDTH/2-65,Game.HEIGHT/2,150,60);

        graphics.setFont(new Font("arial", 1, 40));
        graphics.drawString("Game Over", Game.WIDTH/2-100,Game.HEIGHT/5);

        graphics.setFont(new Font("arial", 1, 20));
        graphics.drawString("Level: " + statusBar.getLevel(), Game.WIDTH/2-50,Game.HEIGHT/4);
        graphics.drawString("Restart", Game.WIDTH/2-30,Game.HEIGHT/2+30);
    }
}
