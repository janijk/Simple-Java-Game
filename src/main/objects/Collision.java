package main.objects;

import main.enums.ID;
import main.util.Handler;

import java.awt.*;

public class Collision  extends  GameObject{
    private int timer;
    private Handler handler;
    public Collision(float x, float y, int width, int height, Handler handler) {
        super(x, y, ID.Collision, width, height);
        this.timer = 1;
        this.handler = handler;
    }

    @Override
    public void tick() {
        if (++timer > 15){
            handler.removeGameObject(this);
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(125,15,33));
        graphics.drawRoundRect(
                (int) x-timer, (int) y-timer, width+timer*2, height+timer*2,100,100);

    }
}
