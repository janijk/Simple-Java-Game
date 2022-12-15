package main.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferedImageLoader {
    private BufferedImage buffImg;
    private static String path = "../resources/";

    public  BufferedImage loadImage(String img) throws IOException {
        return ImageIO.read(getClass().getResource(path+img));
    }
}
