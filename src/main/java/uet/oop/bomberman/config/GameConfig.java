package uet.oop.bomberman.config;

public class GameConfig {
    public static final String NAME = "Bomberman";
    public static final int WIDTH = 496;
    public static final int HEIGHT = 416;
    public static final int TILE_SIZE = 32;

    public static final int SCALED_FACTOR = 2;

    public static final String ICON_PATH = "/icon/icon.png";

    public static final String[] LEVEL_DATA = new String[] {
            "src/main/resources/levels/Level1.txt",
            "src/main/resources/levels/Level2.txt",
            "src/main/resources/levels/Level3.txt",
            "src/main/resources/levels/Level4.txt",
            "src/main/resources/levels/Level5.txt",
            "src/main/resources/levels/Level6.txt",
            "src/main/resources/levels/Level7.txt",
    };

    public static final int LEVEL_MAX = LEVEL_DATA.length;
}
