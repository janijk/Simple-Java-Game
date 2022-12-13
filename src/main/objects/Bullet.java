package main.objects;

import main.enums.ID;

import java.awt.*;

public class Bullet extends GameObject{

    public Bullet(float x, float y, ID id, int width, int height) {
        super(x, y, id, width, height);
    }

    @Override
    public void tick() {
        x += speedX;
        y += speedY;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.orange);
        graphics.fillRect((int) x, (int) y, 4, 4);
    }
}
