package main.objects.enemies;

import main.Game;
import main.StatusBar;
import main.enums.ID;
import main.enums.State;
import main.objects.Bullet;
import main.objects.GameObject;
import main.objects.animations.Images;
import main.util.EnemyRandomizer;
import main.util.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BossEnemy extends GameObject {
    private int stage, rounds, timer, spawnEnemyTimer, shootInterval, shotsFired, speedInc;
    private float angle;
    private Handler handler;
    private StatusBar statusBar;
    private BufferedImage bossImg;

    public BossEnemy(Handler handler, StatusBar statusBar) {
        super(Game.WIDTH/2, -50, ID.BossEnemy, 45, 45);
        this.handler = handler;
        this.statusBar = statusBar;
        speedX = 0;
        speedY = 0.6f;
        this.stage = 1;
        this.angle = 270;
        this.rounds = -1;
        this.timer = 300;
        this.shootInterval = 15;
        this.spawnEnemyTimer = 50;
        bossImg =  Images.getImage(1,1,64,64 ); // Get the right image
    }

    @Override
    public void tick() {
        if (timer-- >= 0) return;

        x += speedX;
        y += speedY;

        if (stage == 1){
            if (shotsFired++ >= 1000) nextStage(75);

            if (y >= 75) stop();

            // Shoot a bullet at set intervals
            if (shootInterval-- <= 0){
                shootInterval = 15;
                handler.addGameObject(new Bullet(x,y,4,4, handler, ID.Player));
            }

        } else if (stage == 2){
            if (rounds == 3) nextStage(250);

            if (angle++ > 360){
                angle = 0;
                rounds++;
            }

            // Calculate next x,y position at the movement circles circumference
            x = (float) (Game.WIDTH/2 + 365 * Math.cos(Math.toRadians(angle)));
            y = (float) (Game.HEIGHT/2 + 365 * Math.sin(Math.toRadians(angle)));

            // Spawn basic enemies at set intervals
            if (spawnEnemyTimer-- <= 0) {
                if (Math.random() >= 0.5) {
                    GameObject go = new BasicEnemy((int) x, (int) y, handler);
                    go.setSpeedY(5 * -1);
                    go.setSpeedX(5 * -5);
                    handler.addGameObject(go);
                } else {
                    handler.addGameObject(new BasicEnemy((int) x, (int) y, handler));
                }
                spawnEnemyTimer = 50;
            }

        } else if (stage == 3) {
            if (rounds == 15) nextStage(75);
            
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
            if (rounds == 13 && shotsFired >= 1500) nextStage(75);

            if (rounds == 0 && moveToStartingLocation()) {
                shotsFired = 0;
                rounds++;
            }

            if (rounds > 0) {
                // Spawn two random enemies at set intervals until rounds reach 13
                if (shotsFired++ % 50 == 0 && rounds <= 12) { //////////////////
                    handler.addGameObject(EnemyRandomizer.createRandomEnemy(handler));
                    handler.addGameObject(EnemyRandomizer.createRandomEnemy(handler));
                    rounds++;
                }

                // Shoot a bullet at set intervals
                if (shootInterval-- <= 0) {
                    shootInterval = 20;
                    handler.addGameObject(new Bullet(x, y, 4, 4, handler, ID.Player));
                }
            }

            // Ensure that after round 11 at least 23 random enemies exists
            if (rounds > 11 && handler.amountOfGameObjects() < 25){
                handler.addGameObject(EnemyRandomizer.createRandomEnemy(handler));
            }

        } else if (stage == 5) {
            // Move boss out of visible screen and wrap up boss fight
            speedY = -1;
            if (y < -30 && rounds++ > 200){
                handler.removeAllExceptPlayer();
                statusBar.plusLevel();
                Game.state = State.GAME;
            }
        }

    }

    @Override
    public void render(Graphics graphics) {
        /*graphics.setColor(Color.red);
        graphics.fillRoundRect((int)x,(int)y,width,height,50,50);*/

        graphics.drawImage(bossImg, (int) x, (int) y,null);
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
