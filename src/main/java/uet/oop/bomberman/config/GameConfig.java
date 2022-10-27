package uet.oop.bomberman.config;

public class GameConfig {
    public static final String NAME = "Bomberman";
    public static double ZOOM = 1.25;
    public static final int SCENE_WIDTH = 32 * 20;
    public static final int SCENE_HEIGHT = 32 * 15;
    public static final int WIDTH = 32 * 18;
    public static final int HEIGHT = 32 * 13;
    public static final int TILE_SIZE = 32;
    public static final int SCALED_FACTOR = 2;
    public static final String ICON_PATH = "/icon/icon1.png";
    public static final String[] LEVEL_DATA = new String[] {
            "src/main/resources/data/levels/Level1.txt",
            "src/main/resources/data/levels/Level2.txt",
            "src/main/resources/data/levels/Level3.txt",
            "src/main/resources/data/levels/Level4.txt",
            "src/main/resources/data/levels/Level5.txt",
            "src/main/resources/data/levels/Level6.txt",
            "src/main/resources/data/levels/Level7.txt",
            "src/main/resources/data/levels/Level8.txt",
    };
    public static final int LEVEL_MAX = LEVEL_DATA.length;
}
