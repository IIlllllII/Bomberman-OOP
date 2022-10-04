package uet.oop.bomberman.core.scenes;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.maps.Map;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.config.PlayerStatus;
import uet.oop.bomberman.core.EntitiesManager;

public class PlayScene {
    private final Group root;
    private Canvas canvas;
    private GraphicsContext gc;
    private Map map;

    public PlayScene() {
        canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root = new Group();

        Button playButton = new Button("BACK");
        playButton.setOnAction(event -> {
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
        });

        root.getChildren().addAll(canvas, playButton);

        map = new Map();
        EntitiesManager.getInstance().players.add(new Bomber(10, 10));
    }

    public Group getRoot() {
        return root;
    }

    public void update() {
        root.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            Direction currentDirection = null;
            if (code == KeyCode.RIGHT || code == KeyCode.D) {
                currentDirection = Direction.RIGHT;
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                currentDirection = Direction.LEFT;
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                currentDirection = Direction.UP;
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                currentDirection = Direction.DOWN;
            }

            if (currentDirection != null) {
                EntitiesManager.getInstance().players.get(0).setPlayerStatus(PlayerStatus.MOVING);
                EntitiesManager.getInstance().players.get(0).setDirection(currentDirection);
            } else {
                EntitiesManager.getInstance().players.get(0).setPlayerStatus(PlayerStatus.IDLE);
            }

            if (event.getCode() == KeyCode.N) {
                map.nextLevel();
            }
        });

        root.setOnKeyReleased(event -> {
            EntitiesManager.getInstance().players.get(0).setPlayerStatus(PlayerStatus.IDLE);
        });

        EntitiesManager.getInstance().players.forEach(
                Entity::update
        );
    }

    public  void render() {
        gc.clearRect(0, 0, GameConfig.WIDTH, GameConfig.WIDTH);
        map.render(gc);
        EntitiesManager.getInstance().players.forEach(
                entity -> entity.render(gc)
        );
    }
}
