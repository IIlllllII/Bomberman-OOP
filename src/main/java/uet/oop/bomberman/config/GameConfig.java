package uet.oop.bomberman.config;

public class GameConfig {

    public static final String NAME = "Bomberman";

    public static final int WIDTH = 496;

    public static final int HEIGHT = 416;

    public static final int PLAYER_WIDTH = 18;

    public static final int PLAYER_HEIGHT = 21;
    public static final int SCALED_FACTOR = 2;

    public static final int FRAME_RATE = 60;

    public static final String ICON_PATH = "/icon/icon.png";

    public static final String GREETING_PATH =
            "src/main/resources/backgrounds/greeting.jpg";

    public static final String MAIN_MENU_PATH =
            "src/main/resources/backgrounds/main_menu.jpg";

    public static final String SETTINGS_PATH =
            "src/main/resources/backgrounds/settings.jpg";

    public static final String TUTORIALS_PATH =
            "src/main/resources/backgrounds/tutorials.jpg";

    public static final String HIGH_SCORE_PATH =
            "src/main/resources/backgrounds/high_score.jpg";

    public static final String ENDING_PATH =
            "src/main/resources/backgrounds/ending.jpg";

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
