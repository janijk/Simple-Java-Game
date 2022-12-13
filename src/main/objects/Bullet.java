package main.objects;

import main.enums.ID;
import main.util.Handler;

import java.awt.*;

public class Bullet extends GameObject{
    private GameObject target;
    private int bulletVelocity;

    public Bullet(float x, float y, int width, int height, Handler handler, ID target) {
        super(x, y, ID.Bullet, width, height);
        this.bulletVelocity  = 8;
        this.target = handler.getGameObject(target);
        fire();
    }

    // Set the bullet to move from shooters to targets position
    public void fire(){
        // Calculate the angle of target to shooter
        float angle = (float) Math.atan2(target.getY() + 10 - y, target.getX() + 10 - x);

        // Calculate the bullets movement direction
        speedX = (float) (bulletVelocity * Math.cos(angle));
        speedY = (float) (bulletVelocity * Math.sin(angle));
    }

    @Override
    public void tick() {
        x += speedX;
        y += speedY;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.orange);
        graphics.fillRect((int) x, (int) y, width, height);
    }
}