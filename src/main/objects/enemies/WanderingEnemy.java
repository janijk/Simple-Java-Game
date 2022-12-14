package main.objects.enemies;

import main.Game;
import main.enums.ID;
import main.objects.GameObject;

import java.awt.*;

public class WanderingEnemy extends GameObject {
    private int timer, velocity;

    public WanderingEnemy(float x, float y) {
        super(x, y, ID.WanderingEnemy, 30, 30);
        this.timer = 150;
        this.velocity = 2;
    }

    @Override
    public void tick() {
        x += speedX;
        y += speedY;

        // When timer is out -> reset timer and set direction to new random point
        if (timer-- <= 0){
            timer = 150;

            // Calculate angle of random point from current point
            float angle = (float) Math.atan2(
                    (Math.random() * Game.HEIGHT) - (Math.random() * Game.HEIGHT),
                    (Math.random() * Game.WIDTH) - (Math.random() * Game.WIDTH)
            );

            // Calculate the movement direction
            speedX = (float) (velocity * Math.cos(angle));
            speedY = (float) (velocity * Math.sin(angle));
        }

        // Reverse direction upon reaching screen boundary
        if (y < 0 || y > Game.HEIGHT-70) speedY *= -1;
        if (x < 0 || x > Game.WIDTH-50) speedX *= -1;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.CYAN);
        graphics.drawRect((int) x, (int) y, width, height);
    }
}