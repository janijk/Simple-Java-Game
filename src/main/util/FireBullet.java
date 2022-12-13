package main.util;

import main.enums.ID;
import main.objects.Bullet;
import main.objects.GameObject;

public class FireBullet {
    private Handler handler;
    private GameObject shooter;
    private GameObject target;

    public FireBullet(Handler handler) {
        this.handler = handler;
        findShooter();
    }

    public FireBullet(Handler handler, GameObject shooter, ID target) {
        this.handler = handler;
        this.shooter = shooter;
        findTarget(target);
    }

    private void findShooter(){
        shooter = handler.getGameObject(ID.Player);
    }

    private void findTarget(ID target){
        this.target = handler.getGameObject(target);
    }

    public void fire(){
        if (shooter != null){
            GameObject bullet = new Bullet(shooter.getX(), shooter.getY(), ID.Bullet,4,4);
            handler.addGameObject(bullet);
            float angle = (float) Math.atan2(target.getY()+10-shooter.getY(),target.getX()+10- shooter.getX());
            int bulletVelocity = 8;
            bullet.setSpeedX((float) ((bulletVelocity) * Math.cos(angle)));
            bullet.setSpeedY((float) ((bulletVelocity) * Math.sin(angle)));
        }
    }
}
