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
import uet.oop.bomberman.config.GameScene;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameManager extends Application {

    private boolean quit;
    private static GraphicsContext gc;
    private Canvas canvas;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> grasses = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> items = new ArrayList<>();

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

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.N) {
                    if (SceneManager.getScreen() == GameScene.PLAYING) {
                        entities = new ArrayList<>();
                        grasses = new ArrayList<>();
                        stillObjects = new ArrayList<>();
                        items = new ArrayList<>();
                        SceneManager.setLevel(SceneManager.getLevel() + 1);
                    } else {
                        SceneManager.setScreen(SceneManager.getScreen().next());
                    }
                }
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //System.out.println((double) now / 1_000_000_000);
                try {
                    SceneManager.loadCurrentScene();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                if (SceneManager.getScreen() == GameScene.PLAYING) {
                    renderGame();
                    updateGame();
                } else {
                    renderBackground();
                }
            }
        };
        timer.start();
    }

    public void renderGame() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(entity -> entity.render(gc));
        stillObjects.forEach(entity -> entity.render(gc));
        entities.forEach(entity -> entity.render(gc));
    }

    public void updateGame() {
        entities.forEach(Entity::update);
    }

    public void renderBackground() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(SceneManager.getBackground(), 0, 0);
    }
}
