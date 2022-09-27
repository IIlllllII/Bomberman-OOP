package uet.oop.bomberman.config;

public enum GameScene {
    GREETING,
    MAIN_MENU,
    SETTINGS,
    TUTORIAL,
    HIGHSCORE,
    PLAYING,
    ENDING;

    static public final GameScene[] values = values();

    public GameScene prev() {
        return values[(ordinal() - 1  + values.length) % values.length];
    }
    public GameScene next() {
        return values[(ordinal() + 1) % values.length];
    }
}