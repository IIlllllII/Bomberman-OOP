package uet.oop.bomberman.core;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.config.GameConfig;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class GameManager {
    public static double FPS = 0;
    public static boolean MUTEMUSIC;

    private static Stage stage;
    private static Scene WINDOW;
    private static GraphicsContext render;
    private static Canvas canvas;
    private LinkedList<Image> icon;

    double x = -10, y = -10;
    public GameManager() {
        MUTEMUSIC = false;

        canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        render = canvas.getGraphicsContext2D();
        Group group = new Group();
        group.getChildren().addAll(canvas);

        Scene scene = new Scene(group, GameConfig.WIDTH, GameConfig.HEIGHT);
        WINDOW = scene;

        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(GameConfig.NAME);
        stage.setScene(WINDOW);
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
            setCursor(canvas);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        InputHandler inputHandler = InputHandler.getInstance();
        inputHandler.handleKeyPressedEvent(scene);

        addLoop();
    }

    private void setCursor(Canvas canvas) throws URISyntaxException {
        canvas.setCursor(Cursor.cursor(GameManager.class.getResource("/cursor/normal.png").toURI().toString()));
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
                    System.out.println(FPS);
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

    public static GraphicsContext getGraphicsContext() {
        return render;
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    public void render() {
        try {
            ScreenManager.renderCurrentScreen();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {

    }
}
