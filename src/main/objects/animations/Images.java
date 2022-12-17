package main.objects.animations;

import java.awt.image.BufferedImage;

public class Images {
    private static BufferedImage buffImg;

    public Images(BufferedImage buffImg) {
        this.buffImg = buffImg;
    }

    public static BufferedImage getImage(int col, int row, int width, int height){
        return buffImg.getSubimage(col*32-32, row*32-32, width, height);
    }
}
