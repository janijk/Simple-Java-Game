package main.objects;

import main.enums.ID;
import main.util.Handler;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Trail extends GameObject {
    private float life, alpha = 0.65f;
    private Handler handler;
    private Color color;

    public Trail(float x, float y, int width, int height, float life, ID id, Handler handler, Color color) {
        super(x, y, id, width, height);
        this.life = life;
        this.handler = handler;
        this.color = color;
    }

    private AlphaComposite makeTransparent(float alpha){

        int type = AlphaComposite.SRC_OVER;
        return AlphaComposite.getInstance(type, alpha);
    }

    @Override
    public void tick() {
        if (alpha > life){
            alpha -= life;
        }else handler.removeGameObject(this);
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphs2d = (Graphics2D) graphics;
        graphs2d.setComposite(makeTransparent(alpha));

        graphics.setColor(color);
        graphics.fillRoundRect((int)x,(int)y,width, height,50,50);

        graphs2d.setComposite(makeTransparent(1));
    }

    @Override
    public Rectangle2D getBounds() {
        return null;
    }
}
