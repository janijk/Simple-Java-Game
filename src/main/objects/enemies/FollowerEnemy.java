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

            if (x + width + speedX > go.getX() && x + speedX < go.getX() + go.getWidth() &&
                    y + height > go.getY() && y < go.getY() + go.getHeight()) {
                speedX *= -1;
            }
            if (x + width > go.getX() && x < go.getX() + go.getWidth() &&
                    y + go.getHeight() + speedY > go.getY() && y + speedY < go.getY() + go.getHeight()) {
                speedY *= -1;
            }
        }
    }

    @Override
    public void tick() {
        float diffX = x - player.getX() -8;
        float diffY = y - player.getY() -8;
        float dist = (float) Math.sqrt(
                (x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY())
        );

        speedX = (float) ((-1.7/dist) * diffX);
        speedY = (float) ((-1.7/dist) * diffY);

        checkCollisionWithOtherFollowers();
        x += speedX;
        y += speedY;

        if (y < 0 || y > Game.HEIGHT-50) speedY *= -1;
        if (x < 0 || x > Game.WIDTH-40) speedX *= -1;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.green);
        graphics.fillRoundRect((int)x,(int)y,width,height,50,50);
    }
}
