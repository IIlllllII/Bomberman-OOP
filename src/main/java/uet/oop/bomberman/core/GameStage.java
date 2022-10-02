package uet.oop.bomberman.core;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.config.GameConfig;

import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class GameStage {
    // Need to make singleton class
    private static GameStage instance = null;
    private double FPS;
    private Stage stage;
    private SceneManager sceneManager;

    private LinkedList<Image> icon;

    public static GameStage getInstance() {
        if (instance == null) {
            instance = new GameStage();
        }
        return instance;
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


        icon = new LinkedList<>();
        try {
            icon.add(new Image(getClass().getResource("/icon/icon.png").toURI().toString()));
            stage.getIcons().add(icon.get(0));
            //setCursor(canvas);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        addLoop();
    }

    private void setCursor(Canvas canvas) throws URISyntaxException {
        canvas.setCursor(Cursor.cursor(GameStage.class.getResource("/cursor/normal.png").toURI().toString()));
    }

    private void addLoop() {
        (new AnimationTimer() {
            private LocalDateTime start = LocalDateTime.now();
            private LocalDateTime stop = LocalDateTime.now();
            private LocalDateTime calcStart = LocalDateTime.now();
            private int frame = 0;

            private void getFps() {
                frame++;
                long time = Duration.between(calcStart, stop).toMillis();
                if (time >= 1000) {
                    FPS = frame;
                    frame = 0;
                    calcStart = LocalDateTime.now();
                }
            }

            @Override
            public void handle(long l) {
                stop = LocalDateTime.now();
                long delay = Duration.between(start, stop).toMillis();
                if (delay >= 14) {
                    getFps();
                    start = LocalDateTime.now();
                    render();
                    update();
                }
            }
        }).start();
    }

    public void render() {
        sceneManager.render();
    }

    public void update() {
        sceneManager.update();
    }
}
