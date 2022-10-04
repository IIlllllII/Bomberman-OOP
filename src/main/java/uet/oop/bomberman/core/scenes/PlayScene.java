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

    private EntitiesManager entitiesManager = EntitiesManager.getInstance();

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
        entitiesManager.players.add(new Bomber(10, 10));
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
            }
            if (code == KeyCode.LEFT || code == KeyCode.A) {
                currentDirection = Direction.LEFT;
            }
            if (code == KeyCode.UP || code == KeyCode.W) {
                currentDirection = Direction.UP;
            }
            if (code == KeyCode.DOWN || code == KeyCode.S) {
                currentDirection = Direction.DOWN;
            }

            //Demo "die" status
            //TODO: remove it later.
            if (code == KeyCode.M) {
                entitiesManager.players.get(0).setPlayerStatus(PlayerStatus.DEAD);
            }

            if (code == KeyCode.N) {
                map.nextLevel();
            }

            if (currentDirection != null) {
                entitiesManager.players.get(0).setPlayerStatus(PlayerStatus.MOVING);
                entitiesManager.players.get(0).setDirection(currentDirection);
            } else {
                if (entitiesManager.players.get(0).getPlayerStatus() != PlayerStatus.DEAD) {
                    entitiesManager.players.get(0).setPlayerStatus(PlayerStatus.IDLE);
                }
            }
        });

        root.setOnKeyReleased(event -> {
            if (entitiesManager.players.get(0).getPlayerStatus() != PlayerStatus.DEAD) {
                entitiesManager.players.get(0).setPlayerStatus(PlayerStatus.IDLE);
            }
        });

        entitiesManager.players.forEach(
                Entity::update
        );
    }

    public  void render() {
        gc.clearRect(0, 0, GameConfig.WIDTH, GameConfig.WIDTH);
        map.render(gc);
        entitiesManager.players.forEach(
                entity -> entity.render(gc)
        );
    }
}
