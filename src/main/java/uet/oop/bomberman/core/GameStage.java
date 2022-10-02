package uet.oop.bomberman.core;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.config.GameConfig;

import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * This class applies `Singleton pattern`
 * with Bill Pugh Singleton Implementation.
 *
 * For more information:
 * https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
 */

public class GameStage {
    private long FPS;
    private Stage stage;
    private SceneManager sceneManager;

    private Image icon;

    private static class SingletonHelper {
        private static final GameStage INSTANCE = new GameStage();
    }
    public static GameStage getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private GameStage() {
        FPS = 0;
        sceneManager = SceneManager.getInstance();

        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(GameConfig.NAME);
        stage.setScene(sceneManager.getScene());
        stage.centerOnScreen();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();

        try {
            icon = new Image(getClass().getResource("/icon/icon.png").toURI().toString());
            stage.getIcons().add(icon);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        (new AnimationTimer() {
            long lastTimestamp;
            @Override
            public void handle(long now) {
                long deltaTimeMili = (now - lastTimestamp) / 1_000_000;
                if (deltaTimeMili >= 14) {
                    lastTimestamp = now;
                    render();
                    update();
                }
            }
        }).start();
    }

    private void render() {
        sceneManager.render();
    }

    private void update() {
        sceneManager.update();
    }
}
