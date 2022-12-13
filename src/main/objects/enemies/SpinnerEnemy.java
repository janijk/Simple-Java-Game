package main.objects.enemies;

import main.Game;
import main.enums.ID;
import main.objects.GameObject;
import main.objects.animations.Trail;
import main.util.Handler;

import java.awt.*;

public class SpinnerEnemy extends GameObject {
    private float radius, centerX, centerY;
    private float angle; // Angle of the radius to objects current position
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

    // Ensure SpinnerEnemy doesn't go outside screen by not allowing center point to be too close of screen borders
    private void ensureBoundaries(){
        centerX = centerX < radius ? (int) (radius + 20) : centerX;
        centerX = centerX + radius > Game.WIDTH ? (int) (Game.WIDTH - radius - 30) : centerX;
        centerY = centerY < radius ? (int) (radius + 20) : centerY;
        centerY = centerY + radius > Game.HEIGHT ? (int) (Game.HEIGHT - radius - 60) : centerY;
    }

    @Override
    public void tick() {
        angle += 1.5;
        if (angle > 360) angle = 1;

        // Calculate next x,y position at the circles circumference
        x = (float) (centerX + radius * Math.cos(Math.toRadians(angle)));
        y = (float) (centerY + radius * Math.sin(Math.toRadians(angle)));

        // Trailing movement effect
        handler.addGameObject(new Trail(x,y,width,height, 0.015f, ID.Trail,handler,new Color(170,0,170)));
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(128,0,128));
        graphics.fillRoundRect((int)x,(int)y,width,height,50,50);
    }
}
