package uet.oop.bomberman.core;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.config.GameScreen;

/**
 * This class applies `Singleton pattern`
 * with Bill Pugh Singleton Implementation.
 *
 * For more information:
 * https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
 */
public class InputHandler {
    private InputHandler() {}

    private static class SingletonHelper {
        static final InputHandler INSTANCE = new InputHandler();
    }

    public static InputHandler getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void handleKeyPressedEvent(Scene scene) {
        EntitiesManager entitiesManager = EntitiesManager.getInstance();

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            /**
             * Press the key N:
             * go to next screen or next level.
             */
            if (code == KeyCode.N) {
                if (ScreenManager.getScreen() == GameScreen.PLAYING) {
                    entitiesManager.renewEntities();
                    ScreenManager.nextLevel();
                } else {
                    ScreenManager.nextScreen();
                }
            }
            /**
             * Press the key P:
             * go to previous screen or previous level.
             */
            if (code == KeyCode.P) {
                if (ScreenManager.getScreen() == GameScreen.PLAYING) {
                    entitiesManager.renewEntities();
                    ScreenManager.prevLevel();
                } else {
                    ScreenManager.prevScreen();
                }
            }
        });
    }
}
