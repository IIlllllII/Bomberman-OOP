package uet.oop.bomberman.core.scenes;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.config.PlayerStatus;
import uet.oop.bomberman.core.Camera;
import uet.oop.bomberman.core.EntitiesManager;

import java.util.ArrayList;
import java.util.List;

public class PlayScene {
    private final Group root;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final LevelMap levelMap;

    private final EntitiesManager entitiesManager = EntitiesManager.getInstance();

    private final List<KeyCode> inputList = new ArrayList<>();

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
        entitiesManager.players.add(new Bomber(10, 10, 16, 22));
        Camera.getInstance().setInfo(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
    }

    public Group getRoot() {
        return root;
    }

    public void update() {
        root.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (! inputList.contains(code)) {
                inputList.add(code);
            }
        });
        root.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            inputList.remove(code);
        });

        Direction currentDirection = null;
        if (inputList.contains(KeyCode.RIGHT) || inputList.contains(KeyCode.D)) {
            currentDirection = Direction.RIGHT;
        }
        if (inputList.contains(KeyCode.LEFT) || inputList.contains(KeyCode.A)) {
            currentDirection = Direction.LEFT;
        }
        if (inputList.contains(KeyCode.UP) || inputList.contains(KeyCode.W)) {
            currentDirection = Direction.UP;
        }
        if (inputList.contains(KeyCode.DOWN) || inputList.contains(KeyCode.S)) {
            currentDirection = Direction.DOWN;
        }

        //Demo "die" status
        //TODO: remove it later.
        if (inputList.contains(KeyCode.M)) {
            entitiesManager.players.get(0).setPlayerStatus(PlayerStatus.DEAD);
        }

        if (inputList.contains(KeyCode.N)) {
            levelMap.nextLevel();
            inputList.remove(KeyCode.N);
        }

        if (currentDirection != null) {
            entitiesManager.players.get(0).setPlayerStatus(PlayerStatus.MOVING);
            entitiesManager.players.get(0).setDirection(currentDirection);
        } else {
            if (entitiesManager.players.get(0).getPlayerStatus() != PlayerStatus.DEAD) {
                entitiesManager.players.get(0).setPlayerStatus(PlayerStatus.IDLE);
            }
        }

        Camera.getInstance().update(levelMap);

        entitiesManager.players.forEach(
                entity -> entity.update(levelMap)
        );
    }

    public  void render() {
        gc.clearRect(0, 0, GameConfig.WIDTH, GameConfig.WIDTH);
        levelMap.render(gc);
        entitiesManager.players.forEach(
                entity -> entity.render(gc)
        );
    }
}
