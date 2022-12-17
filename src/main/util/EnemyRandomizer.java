package main.util;

import main.Game;
import main.enums.ID;
import main.objects.GameObject;
import main.objects.enemies.*;

public class EnemyRandomizer {

    // Number of enemies to select from
    private static int amountOfEnemies = 5;

    public static GameObject createRandomEnemy(Handler handler){
        GameObject go = null;

        // Randomize switch key
        int num = (int) (Math.random() * amountOfEnemies) +1;

        // Randomize X and Y coordinate of enemy
        int x = (int) Game.screenLimit((int)(Math.random() * Game.WIDTH-50),0,Game.WIDTH,true);
        int y = (int) Game.screenLimit((int)(Math.random() * Game.HEIGHT-100),0,Game.HEIGHT,false);

        // Randomize SpinnerEnemy's radius
        float radius = (float) ((Math.random() + 1) * 60);

        switch (num){
            case 1:
                go = new BasicEnemy(x, y, handler);
                break;
            case 2:
                go = new FollowerEnemy(x, y, handler);
                break;
            case 3:
                go = new SpinnerEnemy(radius, x, y, handler);
                break;
            case 4:
                go = new ShooterEnemy(x, y, handler);
                break;
            case 5:
                go = new WanderingEnemy(x,y);
            default:
                break;
        }
        return go;
    }
}
