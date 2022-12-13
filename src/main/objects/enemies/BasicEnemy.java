package main.objects.enemies;

import main.Game;
import main.objects.GameObject;
import main.enums.ID;
import main.objects.animations.Trail;
import main.util.Handler;

import java.awt.*;

public class BasicEnemy extends GameObject {
    private Handler handler;
    public BasicEnemy(int x, int y, Handler handler) {
        super(x, y, ID.BasicEnemy, 14, 14);
        this.handler = handler;
        speedX = 5;
        speedY = 5;
    }

    @Override
    public void tick() {
        x += speedX;
        y += speedY;

        // Change direction by 180 degrees upon reaching screen boundary
        if (y < 0 || y > Game.HEIGHT-50) speedY *= -1;
        if (x < 0 || x > Game.WIDTH-40) speedX *= -1;

        // Trailing movement effect
        handler.addGameObject(new Trail(x,y,width,height, 0.1f,ID.Trail,handler,Color.red));
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.fillRoundRect((int)x,(int)y,width,height,50,50);
    }
}