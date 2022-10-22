package uet.oop.bomberman.core.stages;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import uet.oop.bomberman.core.Timer;
import uet.oop.bomberman.core.scenes.SceneManager;

/**
 * This class applies `Singleton pattern`
 * with Bill Pugh Singleton Implementation.
 * <p>
 * For more information:
 * https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
 */

public class GameStage {
    private final SceneManager sceneManager;
    private final Timer timer;
    private final Scene scene;

    private static class SingletonHelper {
        private static final GameStage INSTANCE = new GameStage();
    }
    public static GameStage getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private GameStage() {
        sceneManager = SceneManager.getInstance();
        timer = Timer.getInstance();

        scene = sceneManager.getScene();
    }

    public void run() {
        (new AnimationTimer() {
            @Override
            public void handle(long now) {
                timer.update(now / 1_000_000);
                update();
                render();
            }
        }).start();
    }

    public Scene getScene() {
        return scene;
    }

    private void render() {
        sceneManager.render();
    }

    private void update() {
        sceneManager.update();
    }
}
