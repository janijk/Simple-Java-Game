package main.objects;

import main.Game;
import main.enums.ID;
import main.StatusBar;
import main.objects.animations.Collision;
import main.objects.animations.Trail;
import main.util.Handler;
import main.util.KeyInput;

import java.awt.*;
import java.util.LinkedList;

public class Player extends GameObject {
    private Handler handler;
    private StatusBar statusBar;
    private float acc = 5f, dcc = 1f;

    public Player(int x, int y, ID id, Handler handler, StatusBar statusBar) {
        super(x, y, id,24,24);
        this.handler = handler;
        this.statusBar = statusBar;
    }

    // Loop through GameObjects and check for collisions with this
    private void collide(){
        LinkedList<GameObject> goList = handler.getGameObjects();

        for (int i = 0; i < goList.size(); i++){
            GameObject go = goList.get(i);
            ID id = go.getId();
            if (id == ID.Trail || id == ID.ShooterEnemy || id == ID.Collision) continue;

            // Check if Player collides with other GameObject and decrease health accordingly
            if (go != null && getBounds().intersects(go.getBounds())){
                if (id == ID.BasicEnemy || id == ID.SpinnerEnemy || id == ID.WanderingEnemy) {
                    statusBar.decreaseHealth(10);
                }else if (id == ID.FollowerEnemy) {
                    statusBar.decreaseHealth(5);
                }else if (id == ID.Bullet) {
                    statusBar.decreaseHealth(15);
                }else continue;

                // Create collision animation and remove colliding object
                handler.addGameObject(new Collision(x,y,width,height, handler));
                handler.removeGameObject(go);
            }
        }
    }

    // Determine movement direction and set speed
    private void move(){
        // Left arrow [0] | Right arrow [2]
        if (KeyInput.keys[2]) speedX += acc;
        else if (KeyInput.keys[0]) speedX -= acc;
        else {
            if (speedX > 0)speedX -= dcc;
            else if (speedX < 0)speedX += dcc;
        }

        // Up arrow [1] | Down arrow [3]
        if (KeyInput.keys[3]) speedY += acc;
        else if (KeyInput.keys[1]) speedY -= acc;
        else {
            if (speedY > 0) speedY -= dcc;
            else if (speedY < 0) speedY +=dcc;
        }

        // Limit max speed
        speedX = Game.limit(speedX, -5,5);
        speedY = Game.limit(speedY, -5,5);

        x += speedX;
        y += speedY;
    }

    @Override
    public void tick() {
        move();
        collide();

        // Restrict movement to not extend beyond screen
        x = Game.screenLimit(x,0,Game.WIDTH,true);
        y = Game.screenLimit(y,0,Game.HEIGHT,false);

        // Trailing movement effect
        handler.addGameObject(new Trail(x,y,width,height, 0.1f, ID.Trail,handler,Color.white));
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillRoundRect((int)x,(int)y,width,height,50,50);
    }
}
