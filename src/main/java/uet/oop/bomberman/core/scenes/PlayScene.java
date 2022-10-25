package uet.oop.bomberman.core.scenes;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.bomber.Player;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.scenes.game.*;

import java.util.List;

public class PlayScene {
    private final StackPane root;
    private final Group layout1;
    private final GraphicsContext gc;
    private final TopBar topBar;
    private final IntroLevel introLevel;
    private final GameOver gameOver;
    private final LevelMap levelMap = LevelMap.getInstance();
    private final Camera camera = Camera.getInstance();
    private final EntitiesManager entitiesManager = EntitiesManager.getInstance();

    public PlayScene() {
        root = new StackPane();
        root.setStyle("-fx-background-color: #2A2E37;");
        root.setAlignment(Pos.CENTER);

        // LAYOUT 1
        layout1 = new Group();

        Canvas canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        gc = canvas.getGraphicsContext2D();

        layout1.getChildren().addAll(canvas);

        // LAYOUT 2
        BorderPane layout2 = new BorderPane();
        layout2.setStyle("-fx-background-color: transparent;");

        // TOP OF LAYOUT 2
        topBar = TopBar.getInstance();
        layout2.setTop(topBar);

        // RiGHT OF LAYOUT 2
        RightSideBar rightSideBar = RightSideBar.getInstance();
        layout2.setRight(rightSideBar);

        // LAYOUT 3
        introLevel = IntroLevel.getInstance();

        // LAYOUT 4
        gameOver = new GameOver();

        root.getChildren().addAll(layout1, layout2, introLevel, gameOver);

        camera.setInfo(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);

        // Don't call reset
    }

    public void reset() {
        introLevel.reset(1);
        levelMap.reset();
        topBar.reset();
        gameOver.reset();
    }

    public void update(List<KeyCode> inputList) {
        if (introLevel.isDone() && !gameOver.isLose()) {
            if (inputList.contains(KeyCode.ESCAPE)) {
                SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
                inputList.remove(KeyCode.ESCAPE);
            }

            if (inputList.contains(KeyCode.N)) {
                levelMap.nextLevel();
                inputList.remove(KeyCode.N);
            }

            if(entitiesManager.bombers.get(0) instanceof Player){
                Player player = (Player) entitiesManager.bombers.get(0);
                player.handleInput(inputList);
            }

            camera.update();
            entitiesManager.update();
            if (entitiesManager.bombers.get(0).isKilled() ||
                    (topBar.getClock().isDone() && !levelMap.isLevelComplete())) {
                gameOver.setLose(true);
                topBar.getClock().stop();
            }

            // Handle next level
            levelMap.update();

//            Bomber player = entitiesManager.players.get(0);
////        filter.update(player.getX() + player.getWidth() / 2.0 - camera.getX(),
////                player.getY() + player.getHeight() / 2.0 - camera.getY());
        }
    }

    public void render() {
        if (introLevel.isDone()) {
            gc.clearRect(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
            levelMap.render(gc);
            entitiesManager.render(gc);
        }
    }

    public Parent getRoot() {
        return root;
    }

    public void zoom() {
        layout1.setScaleX(GameConfig.ZOOM);
        layout1.setScaleY(GameConfig.ZOOM);
        introLevel.setScaleX(GameConfig.ZOOM);
        introLevel.setScaleY(GameConfig.ZOOM);
    }
}
