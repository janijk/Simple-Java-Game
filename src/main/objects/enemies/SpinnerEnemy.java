package main.objects.enemies;

import main.Game;
import main.enums.ID;
import main.objects.GameObject;
import main.objects.Trail;
import main.util.Handler;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SpinnerEnemy extends GameObject {
    private float angle, radius, centerX, centerY;
    private Handler handler;

    public SpinnerEnemy(float radius, float x, float y, Handler handler) {
        super(x, y, ID.SpinnerEnemy,16,16);
        this.radius = radius;
        this.centerX = x;
        this.centerY = y;
        this.handler = handler;
        this.angle = 1;
        ensureBoundaries();
    }

    private void ensureBoundaries(){
        // Ensure SpinnerEnemy doesn't go outside screen
        x = x < radius ? (int) (radius + 20) : x;
        x = x + radius > Game.WIDTH ? (int) (Game.WIDTH - radius - 20) : x;
        y = y < radius ? (int) (radius + 20) : y;
        y = y + radius > Game.HEIGHT ? (int) (Game.HEIGHT - radius - 20) : y;
    }

    @Override
    public void tick() {
        x = (float) (centerX + radius * Math.cos(Math.toRadians(angle)));
        y = (float) (centerY + radius * Math.sin(Math.toRadians(angle)));
        if (angle > 360) angle =1;
        angle += 1.5;

        handler.addGameObject(new Trail(x,y,width,height, 0.015f, ID.Trail,handler,new Color(170,0,170)));
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(128,0,128));
        graphics.fillRoundRect((int)x,(int)y,width,height,50,50);
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x,y,width,height);
    }
}
