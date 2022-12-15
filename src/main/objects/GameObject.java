package main.objects;

import main.enums.ID;
import main.objects.animations.Images;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class GameObject {

    protected float x, y, speedX, speedY;
    protected ID id;
    protected int width, height;
    protected Images images;
    private static int amountOfGameObjects;
    private int InstanceId;

    public GameObject(float x, float y, ID id, int width, int height/*, Images images*/) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.width = width;
        this.height = height;
        this.images = images;
        this.InstanceId = ++amountOfGameObjects;
    }

    public abstract void tick();
    public abstract void render(Graphics graphics);
    public Rectangle2D getBounds(){
        return new Rectangle2D.Double(x,y,width,height);
    };

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public static int getAmountOfGameObjects() {
        return amountOfGameObjects;
    }

    public int getInstanceId() {
        return InstanceId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
