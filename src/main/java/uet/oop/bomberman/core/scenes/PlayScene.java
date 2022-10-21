package uet.oop.bomberman.core.scenes;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.Camera;
import uet.oop.bomberman.core.scenes.game.Clock;

import java.util.List;

public class PlayScene {
    private final StackPane root;
    private final GraphicsContext gc;
    private static Clock clock;
    private static int score;
    private static Label scoreLabel;
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
        top.setSpacing(200);

        clock = new Clock();
        score = 0;
        scoreLabel = new Label(String.format("SCORE: %06d", score));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font(24));

        top.getChildren().addAll(clock, scoreLabel);
        borderPane.setTop(top);

        root.getChildren().add(borderPane);

        camera.setInfo(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);

        reset();
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

    public void reset() {
        score = 0;
        clock.setTime(Clock.DEFAULT_TIME);
        if (levelMap.getLevel() > 1) {
            levelMap.reset();
        }
    }

    public static void setClock(int time) {
        PlayScene.clock.setTime(time);
    }

    public static Clock getClock() {
        return clock;
    }

    public static void addScore(int amount) {
        score += amount;
        scoreLabel.setText(String.format("SCORE: %06d", score));
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
