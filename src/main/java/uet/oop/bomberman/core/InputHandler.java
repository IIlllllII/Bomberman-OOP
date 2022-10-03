package uet.oop.bomberman.core;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

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
        private static final InputHandler INSTANCE = new InputHandler();
    }

    public static InputHandler getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void handleKeyPressedEvent(Scene scene) {
        EntitiesManager entitiesManager = EntitiesManager.getInstance();

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
        });
    }
}
