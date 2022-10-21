package uet.oop.bomberman.core.scenes;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.Camera;
import uet.oop.bomberman.core.scenes.game.CountDown;

import java.util.List;

public class PlayScene {
    private final StackPane root;
    private final GraphicsContext gc;
    private final CountDown count;
    private final LevelMap levelMap = LevelMap.getInstance();
    private final Camera camera = Camera.getInstance();
    private final EntitiesManager entitiesManager = EntitiesManager.getInstance();

    public PlayScene() {
        root = new StackPane();
        root.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #2A2E37;");

        // center
        Group center = new Group();

        Canvas canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        gc = canvas.getGraphicsContext2D();

        center.getChildren().addAll(canvas);
        borderPane.setCenter(center);

        // top
        HBox top = new HBox(50);
        top.setMaxHeight(32);
        top.setAlignment(Pos.CENTER);

        count = new CountDown();
        count.start();

        top.getChildren().add(count);
        borderPane.setTop(top);

        root.getChildren().add(borderPane);

        camera.setInfo(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
    }

    public Parent getRoot() {
        return root;
    }

    public void zoom() {
        Scale scale = new Scale();
        scale.setPivotX(0);
        scale.setPivotY(0);
        scale.setX(GameConfig.ZOOM);
        scale.setY(GameConfig.ZOOM);
        gc.getCanvas().getTransforms().clear();
        gc.getCanvas().getTransforms().add(scale);
    }

    public void update(List<KeyCode> inputList) {
        if (inputList.contains(KeyCode.ESCAPE)) {
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
            inputList.remove(KeyCode.ESCAPE);
        }

        if (inputList.contains(KeyCode.N)) {
            levelMap.nextLevel();
            inputList.remove(KeyCode.N);
        }

        levelMap.update();
        entitiesManager.players.get(0).handleInput(inputList);

        camera.update();
        entitiesManager.update();
    }

    public void render() {
        gc.clearRect(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
        levelMap.render(gc);

        entitiesManager.render(gc);
    }
}
