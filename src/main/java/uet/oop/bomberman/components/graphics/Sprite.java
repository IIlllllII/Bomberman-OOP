package uet.oop.bomberman.components.graphics;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import uet.oop.bomberman.config.GameConfig;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Sprite {
    private static final int TRANSPARENT_COLOR = 0xffff00ff;
    public final int SPRITE_WIDTH;
    public final int SPRITE_HEIGHT;

    private Point2D posFromSheet;
    public int[] pixels;
    private SpriteSheet sheet;

    public Sprite(int width, int height, int color) {
        SPRITE_WIDTH = width;
        SPRITE_HEIGHT = height;
        pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
        Arrays.fill(pixels, color);
    }

    public Sprite(int side, int x, int y, SpriteSheet sheet, int aw, int ah) {
        this(side, side, x, y, sheet, aw, ah);
    }
    public Sprite(int width, int height, int x, int y, SpriteSheet sheet, int aw, int ah) {
        SPRITE_WIDTH = width;
        SPRITE_HEIGHT = height;
        pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
        posFromSheet = new Point2D(x, y);
        this.sheet = sheet;
        loadPixelFromSheet();
    }

    private void loadPixelFromSheet() {
        for (int y = 0; y < SPRITE_HEIGHT; y++) {
            for (int x = 0; x < SPRITE_WIDTH; x++) {
                pixels[x + y * SPRITE_WIDTH] =
                        sheet.pixels[(int)
                                ((x + posFromSheet.getX())
                                        + (y + posFromSheet.getY()) * sheet.SHEET_WIDTH)
                                ];
            }
        }
    }

    public static Image animate(List<Image> images, double time, double timeTakesPlace) {
        double diff = timeTakesPlace / images.size();

        for (int i = 1; i <= images.size(); i++) {
            if (time < diff * i) {
                return images.get(i - 1);
            }
        }
        return images.get(images.size() - 1);
    }

    public int getWidth() {
        return SPRITE_WIDTH;
    }

    public int getHeight() {
        return SPRITE_HEIGHT;
    }

    public int getPixel(int i) {
        return pixels[i];
    }

    public Image getFxImage() {
        WritableImage wr = new WritableImage(SPRITE_WIDTH, SPRITE_HEIGHT);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < SPRITE_WIDTH; x++) {
            for (int y = 0; y < SPRITE_HEIGHT; y++) {
                if (pixels[x + y * SPRITE_WIDTH] == TRANSPARENT_COLOR) {
                    pw.setArgb(x, y, 0);
                }
                else {
                    pw.setArgb(x, y, pixels[x + y * SPRITE_WIDTH]);
                }
            }
        }
        Image input = new ImageView(wr).getImage();
        return resample(input);
        //return input;
    }

    private Image resample(Image input) {
        final int W = (int) input.getWidth();
        final int H = (int) input.getHeight();

        WritableImage output = new WritableImage(
                W * GameConfig.SCALED_FACTOR,
                H * GameConfig.SCALED_FACTOR
        );

        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int argb = reader.getArgb(x, y);
                for (int dy = 0; dy < GameConfig.SCALED_FACTOR; dy++) {
                    for (int dx = 0; dx < GameConfig.SCALED_FACTOR; dx++) {
                        writer.setArgb(
                                x * GameConfig.SCALED_FACTOR + dx,
                                y * GameConfig.SCALED_FACTOR + dy,
                                argb
                        );
                    }
                }
            }
        }
        return output;
    }
}
