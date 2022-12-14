package main.objects.enemies;

import main.Game;
import main.enums.ID;
import main.objects.Bullet;
import main.objects.GameObject;
import main.util.Handler;

import java.awt.*;

public class BossEnemy extends GameObject {
    private int stage, rounds, timer, spawnEnemyTimer, shootInterval, shotsFired, speedInc;
    private float angle;
    private Handler handler;

    public BossEnemy(Handler handler) {
        super(Game.WIDTH/2, -50, ID.BossEnemy, 45, 45);
        this.handler = handler;
        speedX = 0;
        speedY = 1;
        this.stage = 2; ////////////////////// SET STAGE TO 1 ON PROD
        this.angle = 270;
        this.rounds = 2; ////////////////////// SET ROUNDS TO -1 ON PROD
        this.shootInterval = 15;
        this.spawnEnemyTimer = 50;
    }

    @Override
    public void tick() {
        if (timer-- >= 0) return;

        x += speedX;
        y += speedY;

        if (stage == 1){
            if (shotsFired++ >= 1000) nextStage(75);

            if (y == 75) stop();

            // Shoot a bullet at set intervals
            if (shootInterval-- <= 0){
                shootInterval = 15;
                handler.addGameObject(new Bullet(x,y,4,4, handler, ID.Player));
            }

        } else if (stage == 2){
            if (rounds == 3) nextStage(200);

            if (angle++ > 360){
                angle = 0;
                rounds++;
            }

            // Calculate next x,y position at the movement circles circumference
            x = (float) (Game.WIDTH/2 + 365 * Math.cos(Math.toRadians(angle)));
            y = (float) (Game.HEIGHT/2 + 365 * Math.sin(Math.toRadians(angle)));

            // Spawn basic enemies at set intervals
            if (spawnEnemyTimer-- <= 0) {
                handler.addGameObject(new BasicEnemy((int) x, (int) y,handler));
                spawnEnemyTimer = 50;
            }

        } else if (stage == 3) {
            if (rounds == 2) nextStage(75); ////////////////////// SET ROUNDS TO 15 ON PROD
            
            if (rounds == 0) handler.removeGameObjectsById(ID.BasicEnemy);                

            // Increase movement speed at set intervals
            if (speedInc-- <= 0){
                speedInc = 150;
                rounds++;
                speedX += speedX < 0 ? -2 : 2;
                speedY += speedY < 0 ? -2 : 2;

                // Possibility to reverse traveling direction
                if (Math.random()*10 > 5) {
                    speedY *= -1;
                    speedX *= -1;
                }
            }

            // Reverse direction upon reaching screen boundary
            if (y < 5 || y > Game.HEIGHT-100) speedY *= -1;
            if (x < 5 || x > Game.WIDTH-70) speedX *= -1;

        } else if (stage == 4) {
            if (rounds == 15) nextStage(75);

            if (rounds == 0 && moveToStartingLocation()) rounds++;

        } else if (stage == 5) {
            // FINAL STAGE
        }

    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.fillRoundRect((int)x,(int)y,width,height,50,50);
    }

    private boolean moveToStartingLocation(){
        // Stop if approximately at starting point
        int currentX = (int) x;
        int currentY = (int) y;
        if (currentX <= 610 && currentX >= 590 && currentY <= 85 && currentY >= 65){
            stop();
            return true;
        }

        // Calculate the angle of start to current point
        float angle = (float) Math.atan2(75 + 10 - y, Game.WIDTH/2 + 10 - x);

        // Calculate the movement direction
        speedX = (float) (3 * Math.cos(angle));
        speedY = (float) (3 * Math.sin(angle));

        return false;
    }

    private void nextStage(int timer){
        this.timer = timer;
        stage += 1;
        rounds = 0;
        stop();
    }

    private void stop(){
        speedY = 0;
        speedX = 0;
    }
}
