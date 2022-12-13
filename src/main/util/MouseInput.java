package main.util;

import main.enums.ID;
import main.objects.Bullet;
import main.objects.GameObject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Tracks the shooter and coordinates of mouse clicks to determine where from and where to shoot bullets
public class MouseInput extends MouseAdapter {
    private Handler handler;
    private GameObject shooter;
    private GameObject target;

    public MouseInput(Handler handler) {
        this.handler = handler;
        findShooter();
    }

    public MouseInput(Handler handler, GameObject shooter, ID target) {
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

    public void mousePressed(MouseEvent me){
        int mx = me.getX();
        int my = me.getY();

        if (shooter != null){
            GameObject bullet = new Bullet(shooter.getX(), shooter.getY(), ID.Bullet,4,4);
            handler.addGameObject(bullet);
            float angle = (float) Math.atan2(my - shooter.getY(), mx - shooter.getX());
            int bulletVelocity = 8;
            bullet.setSpeedX((float) ((bulletVelocity) * Math.cos(angle)));
            bullet.setSpeedY((float) ((bulletVelocity) * Math.sin(angle)));
        }
    }
}
