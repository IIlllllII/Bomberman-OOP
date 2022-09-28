package uet.oop.bomberman.config;

public enum GameScreen {
    GREETING,
    MAIN_MENU,
    SETTINGS,
    TUTORIAL,
    HIGHSCORE,
    PLAYING,
    ENDING;

    static public final GameScreen[] values = values();

    public GameScreen prev() {
        return values[(ordinal() - 1  + values.length) % values.length];
    }
    public GameScreen next() {
        return values[(ordinal() + 1) % values.length];
    }
}