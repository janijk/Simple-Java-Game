package main.objects.enemies;

import main.Game;
import main.enums.ID;
import main.objects.GameObject;
import main.util.FireBullet;
import main.util.Handler;

import java.awt.*;

public class ShooterEnemy extends GameObject {
    private int shootInterval;
    private Handler handler;

    public ShooterEnemy(float x, float y, Handler handler) {
        super(x, y, ID.ShooterEnemy, 24, 24);
        this.handler = handler;
        this.shootInterval = 50;
        directionRandomizer();
    }

    private void directionRandomizer(){
        int axis = (int) (Math.random()*2+1);
        int direction = (int) (Math.random()*2+1);

        if (axis == 1) {
            x = direction == 1 ? Game.WIDTH : 0;
            speedX = direction == 1 ? -5 : 5;
        } else {
            y = direction == 1 ? Game.HEIGHT : 0;
            speedY = direction == 1 ? -5 : 5;
        }
    }

    @Override
    public void tick() {
        x += speedX;
        y += speedY;

        if (shootInterval-- <= 0){
            shootInterval = 50;
            FireBullet fb = new FireBullet(handler, this, ID.Player);
            fb.fire();
        }
        if (y < 0 || y > Game.HEIGHT || x < 0 || x > Game.WIDTH){
            handler.removeGameObject(this);
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.orange);
        graphics.fillRoundRect((int)x,(int)y,width,height,50,50);
    }
}
