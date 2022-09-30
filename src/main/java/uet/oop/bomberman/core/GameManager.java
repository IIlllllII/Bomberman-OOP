package uet.oop.bomberman.core;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.config.GameScreen;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameManager extends Application {

    private boolean quit;
    private static GraphicsContext gc;
    private static Canvas canvas;

    public static GraphicsContext getGraphicsContext() {
        return gc;
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(
                Sprite.SCALED_SIZE * GameConfig.WIDTH * 1.0,
                Sprite.SCALED_SIZE * GameConfig.HEIGHT * 1.0
        );
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        stage.setResizable(false);
        stage.setTitle(GameConfig.NAME);
        stage.getIcons().add(new Image(GameConfig.ICON_PATH));
        stage.setScene(scene);
        stage.show();

        InputHandler inputHandler = InputHandler.getInstance();
        inputHandler.handleKeyPressedEvent(scene);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //System.out.println((double) now / 1_000_000_000);
                try {
                    ScreenManager.renderCurrentScreen();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.start();
    }
}
