package main.screens;

import main.*;
import main.enums.ID;
import main.enums.State;
import main.objects.Player;
import main.objects.enemies.BossEnemy;
import main.util.Handler;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter {
    private Handler handler;
    private StatusBar statusBar;

    public Menu(StatusBar statusBar, Handler handler) {
        this.handler = handler;
        this.statusBar = statusBar;
    }

    @Override
    public void mousePressed(MouseEvent e){
        if (Game.state == State.MENU) {
            int mx = e.getX(), my = e.getY();

            // Mouse clicked at 'start' box
            if (mouseAt(mx, my, Game.WIDTH / 2 - 125, Game.HEIGHT / 5, 150, 60)) {
                handler.addGameObject(new Player(Game.WIDTH / 2, Game.HEIGHT / 2, ID.Player, handler, statusBar));
                Game.state = State.GAME;

                handler.addGameObject(new BossEnemy(handler));
            }

            // Mouse clicked at 'info' box
            if (mouseAt(mx, my, Game.WIDTH / 2 - 125, Game.HEIGHT / 3, 150, 60)) {
                Game.state = State.INFO;
            }

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
        graphics.setColor(Color.lightGray);
        graphics.drawRect(Game.WIDTH/2-125,Game.HEIGHT/5,150,60);
        graphics.drawRect(Game.WIDTH/2-125,Game.HEIGHT/3,150,60);

        graphics.setFont(new Font("arial", 1, 40));
        graphics.drawString("Menu", Game.WIDTH/2-100,Game.HEIGHT/7);

        graphics.setFont(new Font("arial", 1, 20));
        graphics.drawString("Start", Game.WIDTH/2-75,Game.HEIGHT/5+30);
        graphics.drawString("Info", Game.WIDTH/2-75,Game.HEIGHT/3+30);
    }
}
