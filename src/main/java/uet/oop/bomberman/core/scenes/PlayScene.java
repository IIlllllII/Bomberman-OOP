package uet.oop.bomberman.core.scenes;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;

public class PlayScene {
    private final Group root;
    private Canvas canvas;
    private GraphicsContext gc;
    private LevelMap levelMap;

    public PlayScene() {
        canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root = new Group();

        Button playButton = new Button("BACK");
        playButton.setOnAction(event -> {
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
        });

        root.getChildren().addAll(canvas, playButton);

        levelMap = new LevelMap();
    }

    public Group getRoot() {
        return root;
    }

    public void update() {
        root.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.N) {
                levelMap.nextLevel();
            }
        });
    }

    public  void render() {
        gc.clearRect(0, 0, GameConfig.WIDTH, GameConfig.WIDTH);
        levelMap.render(gc);
    }
}
