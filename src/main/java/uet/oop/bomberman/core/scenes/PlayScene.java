package uet.oop.bomberman.core.scenes;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.Camera;

import java.util.List;

public class PlayScene {
    private final Group root;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final LevelMap levelMap = LevelMap.getInstance();
    private final Camera camera = Camera.getInstance();
    private final EntitiesManager entitiesManager = EntitiesManager.getInstance();

    public PlayScene() {
        canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        gc = canvas.getGraphicsContext2D();

        root = new Group();
        root.getChildren().addAll(canvas);

        camera.setInfo(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
    }

    public Group getRoot() {
        return root;
    }

    public void update(List<KeyCode> inputList) {
        if (inputList.contains(KeyCode.ESCAPE)) {
            MenuScene.gameMusic.stopMusic();
            MenuScene.menuMusic.playMusic();
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
        }

        if (inputList.contains(KeyCode.N)) {
            levelMap.nextLevel();
            inputList.remove(KeyCode.N);
        }

        entitiesManager.players.get(0).handleInput(inputList);

        // update first
        camera.update();
        entitiesManager.update();
    }

    public void render() {
        gc.clearRect(0, 0, GameConfig.WIDTH, GameConfig.WIDTH);
        levelMap.render(gc);

        entitiesManager.render(gc);
    }
}
