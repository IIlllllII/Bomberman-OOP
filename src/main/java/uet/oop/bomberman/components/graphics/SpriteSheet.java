package uet.oop.bomberman.components.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SpriteSheet {
    private final String path;
    public final int SHEET_WIDTH;
    public final int SHEET_HEIGHT;
    public int[] pixels;
    public BufferedImage image;
    public SpriteSheet(String path, int width, int height) {
        this.path = path;
        SHEET_WIDTH = width;
        SHEET_HEIGHT = height;
        pixels = new int[SHEET_WIDTH * SHEET_HEIGHT];
        loadPixels();
    }

    private void loadPixels() {
        try {
            URL fileUrl = SpriteSheet.class.getResource(path);
            assert fileUrl != null;
            image = ImageIO.read(fileUrl);
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
