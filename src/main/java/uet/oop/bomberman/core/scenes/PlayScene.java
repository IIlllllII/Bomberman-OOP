package uet.oop.bomberman.core.scenes;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.entities.stillobjects.Brick;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.config.PlayerStatus;
import uet.oop.bomberman.core.Camera;
import uet.oop.bomberman.components.entities.EntitiesManager;

import java.util.ArrayList;
import java.util.List;

public class PlayScene {
    private final Group root;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final LevelMap levelMap = LevelMap.getInstance();
    private final Camera camera = Camera.getInstance();
    private final EntitiesManager entitiesManager = EntitiesManager.getInstance();
    private final List<KeyCode> inputList = new ArrayList<>();

    public PlayScene() {
        canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root = new Group();

        Button playButton = new Button("BACK");
        playButton.setOnMouseClicked(event -> {
            MenuScene.gameMusic.stopMusic();
            MenuScene.menuMusic.playMusic();
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
        });

        root.getChildren().addAll(canvas, playButton);
        root.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (!inputList.contains(code)) {
                inputList.add(code);
            }
        });
        root.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            inputList.remove(code);
        });

        entitiesManager.players.add(
                new Bomber(33, 33, 16, 22)
        );
        camera.setInfo(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
    }

    public Group getRoot() {
        return root;
    }

    public void update() {
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

        if  (inputList.contains(KeyCode.B)) {
            entitiesManager.players.get(0).placeBomb();
            inputList.remove(KeyCode.B);
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

        camera.update();

        levelMap.update();

        List<Bomb> bombList = entitiesManager.bombs;
        for (int i = 0; i < bombList.size(); i++){
            if (! bombList.get(i).isDone()) {
                bombList.get(i).update();
            } else {
                bombList.remove(i);
                i--;
            }
        }
        entitiesManager.players.forEach(Entity::update);
        entitiesManager.brokenBricks.forEach(Brick::update);
    }

    public  void render() {
        gc.clearRect(0, 0, GameConfig.WIDTH, GameConfig.WIDTH);
        levelMap.render(gc);
        entitiesManager.bombs.forEach(entity -> entity.render(gc));
        //Render all destroyed bricks
        entitiesManager.brokenBricks.forEach(entity -> entity.render(gc));
        entitiesManager.players.forEach(entity -> entity.render(gc));
    }
}
