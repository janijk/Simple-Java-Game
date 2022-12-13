package main.objects.enemies;

import main.Game;
import main.enums.ID;
import main.objects.GameObject;
import main.util.Handler;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class FollowerEnemy extends GameObject {
    private GameObject player;
    private Handler handler;
    public FollowerEnemy(int x, int y, Handler handler) {
        super(x, y, ID.FollowerEnemy, 14, 14);
        this.handler = handler;
        this.player = handler.getGameObject(ID.Player);
    }


    // Prevent stacking of FollowerEnemies
    private void checkCollisionWithOtherFollowers(){
        List<GameObject> followerList = handler.getGameObjects().stream()
                .filter(o -> o.getId() == ID.FollowerEnemy).collect(Collectors.toList());
        followerList.remove(this);

        for (int i = 0; i < followerList.size(); i++){
            GameObject go = followerList.get(i);

            // Check if the next position at x-axis would overlap with object and reverse direction if so
            if (x + speedX < go.getX() + go.getWidth() && x + width + speedX > go.getX() &&
                     y < go.getY() + go.getHeight() && y + height > go.getY()){
                speedX *= -1;
            }

            // Check if the next position at y-axis would overlap with object and reverse direction if so
            if (y + speedY < go.getY() + go.getHeight() && y + height + speedY > go.getY() &&
                    x < go.getX() + go.getWidth() && x + width > go.getX()) {
                speedY *= -1;
            }
        }
    }

    @Override
    public void tick() {
        float diffX = x - player.getX() ;
        float diffY = y - player.getY() ;

        // Calculate the distance between player and FollowerEnemy
        float dist = (float) Math.sqrt( diffX * diffX + diffY * diffY );

        // Calculate adjacent x,y position to the players direction from current position
        speedX = (float) ((-1.7/dist) * diffX);
        speedY = (float) ((-1.7/dist) * diffY);

        checkCollisionWithOtherFollowers();
        x += speedX;
        y += speedY;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.green);
        graphics.fillRoundRect((int)x,(int)y,width,height,50,50);
    }
}
