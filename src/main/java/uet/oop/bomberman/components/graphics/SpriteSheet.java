package uet.oop.bomberman.components.graphics;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class SpriteSheet {
    private static boolean initialized = false;
    public static Image NES;
    public static Image enemy;

    private final String path;
    public final int SHEET_WIDTH;
    public final int SHEET_HEIGHT;
    public int[] pixels;
    public BufferedImage image;

    public static void init() {
        if (!initialized) {
            try {
                NES = new Image(SpriteSheet.class.getResource("/spriteSheet/NES.png").toURI().toString());
                enemy = new Image(SpriteSheet.class.getResource("/spriteSheet/enemy.png").toURI().toString());
            } catch (URISyntaxException e) {
                System.out.println("Sprite Sheet init");
                throw new RuntimeException(e);
            }
            initialized = true;
        }
    }
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
