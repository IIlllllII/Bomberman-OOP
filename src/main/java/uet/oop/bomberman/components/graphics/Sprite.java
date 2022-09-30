package uet.oop.bomberman.components.graphics;


import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import uet.oop.bomberman.config.GameConfig;

import java.util.Arrays;

public class Sprite {

    public static final int DEFAULT_SIZE = 16;
    public static final int SCALED_SIZE = DEFAULT_SIZE * GameConfig.SCALED_FACTOR;
    private static final int TRANSPARENT_COLOR = 0xffff00ff;
    public final int SPRITE_WIDTH;
    public final int SPRITE_HEIGHT;

    private Point2D posFromSheet;
    public int[] pixels;
    private int actualWidth;
    private int actualHeight;
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
        posFromSheet = new Point2D(x * SPRITE_WIDTH, y * SPRITE_HEIGHT);
        this.sheet = sheet;
        actualWidth = aw;
        actualHeight = ah;
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

//    public static Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2, int animate, int time) {
//        int calc = animate % time;
//        int diff = time / 3;
//        if(calc < diff) {
//            return normal;
//        }
//        if(calc < diff * 2) {
//            return x1;
//        }
//        return x2;
//    }

//    public static Sprite movingSprite(Sprite x1, Sprite x2, int animate, int time) {
//        int diff = time / 2;
//        return (animate % time > diff) ? x1 : x2;
//    }

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
                if ( pixels[x + y * SPRITE_WIDTH] == TRANSPARENT_COLOR) {
                    pw.setArgb(x, y, 0);
                }
                else {
                    pw.setArgb(x, y, pixels[x + y * SPRITE_WIDTH]);
                }
            }
        }
        Image input = new ImageView(wr).getImage();
        return resample(input);
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

    /*
|--------------------------------------------------------------------------
| Board sprites
|--------------------------------------------------------------------------
 */
    public static Sprite grass = new Sprite(
            DEFAULT_SIZE, DEFAULT_SIZE, 6, 0, SpriteSheet.tiles, 16, 16
    );
    public static Sprite brick = new Sprite(
            DEFAULT_SIZE, 7, 0, SpriteSheet.tiles, 16, 16
    );
    public static Sprite wall = new Sprite(
            DEFAULT_SIZE, 5, 0, SpriteSheet.tiles, 16, 16
    );
    public static Sprite portal = new Sprite(
            DEFAULT_SIZE, 4, 0, SpriteSheet.tiles, 14, 14
    );

    public static Sprite player = new Sprite(
            19, 22, 0, 0, SpriteSheet.characters,19, 22
    );

//    /*
//    |--------------------------------------------------------------------------
//    | Bomber Sprites
//    |--------------------------------------------------------------------------
//     */
//    public static Sprite player_up = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.tiles, 12, 16);
//    public static Sprite player_down = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.tiles, 12, 15);
//    public static Sprite player_left = new Sprite(DEFAULT_SIZE, 3, 0, SpriteSheet.tiles, 10, 15);
//    public static Sprite player_right = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.tiles, 10, 16);
//
//    public static Sprite player_up_1 = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.tiles, 12, 16);
//    public static Sprite player_up_2 = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.tiles, 12, 15);
//
//    public static Sprite player_down_1 = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.tiles, 12, 15);
//    public static Sprite player_down_2 = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.tiles, 12, 16);
//
//    public static Sprite player_left_1 = new Sprite(DEFAULT_SIZE, 3, 1, SpriteSheet.tiles, 11, 16);
//    public static Sprite player_left_2 = new Sprite(DEFAULT_SIZE, 3, 2, SpriteSheet.tiles, 12 ,16);
//
//    public static Sprite player_right_1 = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.tiles, 11, 16);
//    public static Sprite player_right_2 = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.tiles, 12, 16);
//
//    public static Sprite player_dead1 = new Sprite(DEFAULT_SIZE, 4, 2, SpriteSheet.tiles, 14, 16);
//    public static Sprite player_dead2 = new Sprite(DEFAULT_SIZE, 5, 2, SpriteSheet.tiles, 13, 15);
//    public static Sprite player_dead3 = new Sprite(DEFAULT_SIZE, 6, 2, SpriteSheet.tiles, 16, 16);
//
//    /*
//    |--------------------------------------------------------------------------
//    | Character
//    |--------------------------------------------------------------------------
//     */
//    //BALLOM
//    public static Sprite balloom_left1 = new Sprite(DEFAULT_SIZE, 9, 0, SpriteSheet.tiles, 16, 16);
//    public static Sprite balloom_left2 = new Sprite(DEFAULT_SIZE, 9, 1, SpriteSheet.tiles, 16, 16);
//    public static Sprite balloom_left3 = new Sprite(DEFAULT_SIZE, 9, 2, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite balloom_right1 = new Sprite(DEFAULT_SIZE, 10, 0, SpriteSheet.tiles, 16, 16);
//    public static Sprite balloom_right2 = new Sprite(DEFAULT_SIZE, 10, 1, SpriteSheet.tiles, 16, 16);
//    public static Sprite balloom_right3 = new Sprite(DEFAULT_SIZE, 10, 2, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite balloom_dead = new Sprite(DEFAULT_SIZE, 9, 3, SpriteSheet.tiles, 16, 16);
//
//    //ONEAL
//    public static Sprite oneal_left1 = new Sprite(DEFAULT_SIZE, 11, 0, SpriteSheet.tiles, 16, 16);
//    public static Sprite oneal_left2 = new Sprite(DEFAULT_SIZE, 11, 1, SpriteSheet.tiles, 16, 16);
//    public static Sprite oneal_left3 = new Sprite(DEFAULT_SIZE, 11, 2, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite oneal_right1 = new Sprite(DEFAULT_SIZE, 12, 0, SpriteSheet.tiles, 16, 16);
//    public static Sprite oneal_right2 = new Sprite(DEFAULT_SIZE, 12, 1, SpriteSheet.tiles, 16, 16);
//    public static Sprite oneal_right3 = new Sprite(DEFAULT_SIZE, 12, 2, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite oneal_dead = new Sprite(DEFAULT_SIZE, 11, 3, SpriteSheet.tiles, 16, 16);
//
//    //Doll
//    public static Sprite doll_left1 = new Sprite(DEFAULT_SIZE, 13, 0, SpriteSheet.tiles, 16, 16);
//    public static Sprite doll_left2 = new Sprite(DEFAULT_SIZE, 13, 1, SpriteSheet.tiles, 16, 16);
//    public static Sprite doll_left3 = new Sprite(DEFAULT_SIZE, 13, 2, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite doll_right1 = new Sprite(DEFAULT_SIZE, 14, 0, SpriteSheet.tiles, 16, 16);
//    public static Sprite doll_right2 = new Sprite(DEFAULT_SIZE, 14, 1, SpriteSheet.tiles, 16, 16);
//    public static Sprite doll_right3 = new Sprite(DEFAULT_SIZE, 14, 2, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite doll_dead = new Sprite(DEFAULT_SIZE, 13, 3, SpriteSheet.tiles, 16, 16);
//
//    //Minvo
//    public static Sprite minvo_left1 = new Sprite(DEFAULT_SIZE, 8, 5, SpriteSheet.tiles, 16, 16);
//    public static Sprite minvo_left2 = new Sprite(DEFAULT_SIZE, 8, 6, SpriteSheet.tiles, 16, 16);
//    public static Sprite minvo_left3 = new Sprite(DEFAULT_SIZE, 8, 7, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite minvo_right1 = new Sprite(DEFAULT_SIZE, 9, 5, SpriteSheet.tiles, 16, 16);
//    public static Sprite minvo_right2 = new Sprite(DEFAULT_SIZE, 9, 6, SpriteSheet.tiles, 16, 16);
//    public static Sprite minvo_right3 = new Sprite(DEFAULT_SIZE, 9, 7, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite minvo_dead = new Sprite(DEFAULT_SIZE, 8, 8, SpriteSheet.tiles, 16, 16);
//
//    //Kondoria
//    public static Sprite kondoria_left1 = new Sprite(DEFAULT_SIZE, 10, 5, SpriteSheet.tiles, 16, 16);
//    public static Sprite kondoria_left2 = new Sprite(DEFAULT_SIZE, 10, 6, SpriteSheet.tiles, 16, 16);
//    public static Sprite kondoria_left3 = new Sprite(DEFAULT_SIZE, 10, 7, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite kondoria_right1 = new Sprite(DEFAULT_SIZE, 11, 5, SpriteSheet.tiles, 16, 16);
//    public static Sprite kondoria_right2 = new Sprite(DEFAULT_SIZE, 11, 6, SpriteSheet.tiles, 16, 16);
//    public static Sprite kondoria_right3 = new Sprite(DEFAULT_SIZE, 11, 7, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite kondoria_dead = new Sprite(DEFAULT_SIZE, 10, 8, SpriteSheet.tiles, 16, 16);
//
//    //ALL
//    public static Sprite mob_dead1 = new Sprite(DEFAULT_SIZE, 15, 0, SpriteSheet.tiles, 16, 16);
//    public static Sprite mob_dead2 = new Sprite(DEFAULT_SIZE, 15, 1, SpriteSheet.tiles, 16, 16);
//    public static Sprite mob_dead3 = new Sprite(DEFAULT_SIZE, 15, 2, SpriteSheet.tiles, 16, 16);
//
    /*
    |--------------------------------------------------------------------------
    | Bomb Sprites
    |--------------------------------------------------------------------------
     */
    public static Sprite bomb = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.tiles, 15, 15);
    public static Sprite bomb_1 = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.tiles, 13, 15);
    public static Sprite bomb_2 = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.tiles, 12, 14);
//
//    /*
//    |--------------------------------------------------------------------------
//    | FlameSegment Sprites
//    |--------------------------------------------------------------------------
//     */
//    public static Sprite bomb_exploded = new Sprite(DEFAULT_SIZE, 0, 4, SpriteSheet.tiles, 16, 16);
//    public static Sprite bomb_exploded1 = new Sprite(DEFAULT_SIZE, 0, 5, SpriteSheet.tiles, 16, 16);
//    public static Sprite bomb_exploded2 = new Sprite(DEFAULT_SIZE, 0, 6, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite explosion_vertical = new Sprite(DEFAULT_SIZE, 1, 5, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_vertical1 = new Sprite(DEFAULT_SIZE, 2, 5, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_vertical2 = new Sprite(DEFAULT_SIZE, 3, 5, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite explosion_horizontal = new Sprite(DEFAULT_SIZE, 1, 7, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_horizontal1 = new Sprite(DEFAULT_SIZE, 1, 8, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_horizontal2 = new Sprite(DEFAULT_SIZE, 1, 9, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite explosion_horizontal_left_last = new Sprite(DEFAULT_SIZE, 0, 7, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_horizontal_left_last1 = new Sprite(DEFAULT_SIZE, 0, 8, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_horizontal_left_last2 = new Sprite(DEFAULT_SIZE, 0, 9, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite explosion_horizontal_right_last = new Sprite(DEFAULT_SIZE, 2, 7, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_horizontal_right_last1 = new Sprite(DEFAULT_SIZE, 2, 8, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_horizontal_right_last2 = new Sprite(DEFAULT_SIZE, 2, 9, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite explosion_vertical_top_last = new Sprite(DEFAULT_SIZE, 1, 4, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_vertical_top_last1 = new Sprite(DEFAULT_SIZE, 2, 4, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_vertical_top_last2 = new Sprite(DEFAULT_SIZE, 3, 4, SpriteSheet.tiles, 16, 16);
//
//    public static Sprite explosion_vertical_down_last = new Sprite(DEFAULT_SIZE, 1, 6, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_vertical_down_last1 = new Sprite(DEFAULT_SIZE, 2, 6, SpriteSheet.tiles, 16, 16);
//    public static Sprite explosion_vertical_down_last2 = new Sprite(DEFAULT_SIZE, 3, 6, SpriteSheet.tiles, 16, 16);
//
//    /*
//    |--------------------------------------------------------------------------
//    | Brick FlameSegment
//    |--------------------------------------------------------------------------
//     */
//    public static Sprite brick_exploded = new Sprite(DEFAULT_SIZE, 7, 1, SpriteSheet.tiles, 16, 16);
//    public static Sprite brick_exploded1 = new Sprite(DEFAULT_SIZE, 7, 2, SpriteSheet.tiles, 16, 16);
//    public static Sprite brick_exploded2 = new Sprite(DEFAULT_SIZE, 7, 3, SpriteSheet.tiles, 16, 16);
//
//    /*
//    |--------------------------------------------------------------------------
//    | Powerups
//    |--------------------------------------------------------------------------
//     */
//    public static Sprite powerup_bombs = new Sprite(DEFAULT_SIZE, 0, 10, SpriteSheet.tiles, 16, 16);
//    public static Sprite powerup_flames = new Sprite(DEFAULT_SIZE, 1, 10, SpriteSheet.tiles, 16, 16);
//    public static Sprite powerup_speed = new Sprite(DEFAULT_SIZE, 2, 10, SpriteSheet.tiles, 16, 16);
//    public static Sprite powerup_wallpass = new Sprite(DEFAULT_SIZE, 3, 10, SpriteSheet.tiles, 16, 16);
//    public static Sprite powerup_detonator = new Sprite(DEFAULT_SIZE, 4, 10, SpriteSheet.tiles, 16, 16);
//    public static Sprite powerup_bombpass = new Sprite(DEFAULT_SIZE, 5, 10, SpriteSheet.tiles, 16, 16);
//    public static Sprite powerup_flamepass = new Sprite(DEFAULT_SIZE, 6, 10, SpriteSheet.tiles, 16, 16);

    /*
    |--------------------------------------------------------------------------
    | New Sprites for each level
    |--------------------------------------------------------------------------
     */
    public static Sprite grass1 = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.newTiles, 32, 32);
    public static Sprite wall1 = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.newTiles, 32, 32);
    public static Sprite brick1 = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.newTiles, 32, 32);

    public static Sprite grass2 = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.newTiles, 32, 32);
    public static Sprite wall2 = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.newTiles, 32, 32);
    public static Sprite brick2 = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.newTiles, 32, 32);

    public static Sprite grass3 = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.newTiles, 32, 32);
    public static Sprite wall3 = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.newTiles, 32, 32);
    public static Sprite brick3 = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.newTiles, 32, 32);

    public static Sprite grass4 = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.newTiles, 32, 32);
    public static Sprite wall4 = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.newTiles, 32, 32);
    public static Sprite brick4 = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.newTiles, 32, 32);

    public static Sprite grass5 = new Sprite(DEFAULT_SIZE, 0, 4, SpriteSheet.newTiles, 32, 32);
    public static Sprite wall5 = new Sprite(DEFAULT_SIZE, 1, 4, SpriteSheet.newTiles, 32, 32);
    public static Sprite brick5 = new Sprite(DEFAULT_SIZE, 2, 4, SpriteSheet.newTiles, 32, 32);

    public static Sprite grass6 = new Sprite(DEFAULT_SIZE, 0, 5, SpriteSheet.newTiles, 32, 32);
    public static Sprite wall6 = new Sprite(DEFAULT_SIZE, 1, 5, SpriteSheet.newTiles, 32, 32);
    public static Sprite brick6 = new Sprite(DEFAULT_SIZE, 2, 5, SpriteSheet.newTiles, 32, 32);

    public static Sprite grass7 = new Sprite(DEFAULT_SIZE, 3, 0, SpriteSheet.newTiles, 32, 32);
    public static Sprite wall7 = new Sprite(DEFAULT_SIZE, 4, 0, SpriteSheet.newTiles, 32, 32);
    public static Sprite brick7 = new Sprite(DEFAULT_SIZE, 5, 0, SpriteSheet.newTiles, 32, 32);
}
