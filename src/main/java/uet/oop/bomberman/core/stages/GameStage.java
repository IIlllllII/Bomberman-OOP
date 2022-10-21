package uet.oop.bomberman.core.stages;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import uet.oop.bomberman.core.Timers;
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
    private final Timers timers;
    private final Scene scene;

    private int totalScore;

    private static class SingletonHelper {
        private static final GameStage INSTANCE = new GameStage();
    }
    public static GameStage getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private GameStage() {
        totalScore = 0;
        sceneManager = SceneManager.getInstance();
        timers = Timers.getInstance();

        scene = sceneManager.getScene();
    }

    public void run() {
        (new AnimationTimer() {
            @Override
            public void handle(long now) {
                timers.update(now / 1_000_000);
                update();
                render();
            }
        }).start();
    }

    public Scene getScene() {
        return scene;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void plusTotalScore(int plusScore) {
        totalScore += plusScore;
    }

    private void render() {
        sceneManager.render();
    }

    private void update() {
        sceneManager.update();
    }
}
