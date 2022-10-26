package uet.oop.bomberman.core.scenes;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.bomber.Bomber;
import uet.oop.bomberman.components.entities.bomber.Player;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.algo.AStar;
import uet.oop.bomberman.core.scenes.game.*;
import uet.oop.bomberman.core.scenes.game.filter.LightFilter;

import java.util.List;

public class PlayScene {
    public enum STATUS {
        INTRO, PLAYING, PAUSE, LOSE, WIN
    }

    private STATUS status;
    private final StackPane root;
    private final Group layout1;            // LAYOUT 1
    private final GraphicsContext gc;
    private final BorderPane layout2;      // LAYOUT 2
    private final TopBar topBar;
    private final BottomBar bottomBar;
    private final IntroLevel introLevel;    // LAYOUT 3
    private final PauseMenu pauseMenu;      // LAYOUT 4
    private final GameOver gameOver;        // LAYOUT 5
    private final LevelMap levelMap = LevelMap.getInstance();
    private final Camera camera = Camera.getInstance();
    private final EntitiesManager entitiesManager = EntitiesManager.getInstance();
    private final LightFilter filter;

    private static class SingletonHelper {
        private static final PlayScene INSTANCE = new PlayScene();
    }

    public static PlayScene getInstance() {
        return PlayScene.SingletonHelper.INSTANCE;
    }

    private PlayScene() {
        root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(#4F917CFF, #00140EFF);");
        root.setAlignment(Pos.CENTER);

        status = STATUS.INTRO;

        // LAYOUT 1
        layout1 = new Group();

        Canvas canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);

        // lag
//        DropShadow dropShadow = new DropShadow(20, Color.LIGHTBLUE);
//        dropShadow.setSpread(0.8);
//        canvas.setEffect(dropShadow);

        gc = canvas.getGraphicsContext2D();

        filter = new LightFilter(150);  // filter 1

        layout1.getChildren().addAll(canvas, filter.getFilter());

        // LAYOUT 2
        layout2 = new BorderPane();
        layout2.setStyle("-fx-background-color: transparent;");

        // TOP OF LAYOUT 2
        topBar = TopBar.getInstance();
        layout2.setTop(topBar);

        // RiGHT OF LAYOUT 2
        RightSideBar rightSideBar = RightSideBar.getInstance();
        layout2.setRight(rightSideBar);

        // BOTTOM OF LAYOUT 2
        bottomBar = BottomBar.getInstance();
        layout2.setBottom(bottomBar);

        // LAYOUT 3
        introLevel = IntroLevel.getInstance();

        // LAYOUT 4
        pauseMenu = PauseMenu.getInstance();

        // LAYOUT 5
        gameOver = new GameOver();

        root.getChildren().addAll(layout1, layout2, introLevel, pauseMenu, gameOver);

        camera.setInfo(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);

        // Don't call reset
    }

    public void reset() {
        System.out.println("call reset");
        levelMap.reset();   // level = 0 & bomber.clear() create new bomber
        topBar.reset();     // reset score
        bottomBar.reset();  // reset default item
    }

    public void setStatus(STATUS status) {
        this.status = status;
        switch (status) {
            case INTRO: {
                IntroLevel.getInstance().reset(levelMap.getLevel());
                layout2.setDisable(true);
                topBar.getClock().pause();
                break;
            }
            case PLAYING: {
                layout1.setDisable(false);
                layout2.setDisable(false);
                topBar.getClock().play();
                break;
            }
            case PAUSE: {
                pauseMenu.setEnable();
                layout2.setDisable(true);
                topBar.getClock().pause();
                break;
            }
            case LOSE: {
                gameOver.setEnable();
                layout2.setDisable(true);
                topBar.getClock().pause();
                break;
            }
            case WIN: {
                layout2.setDisable(true);
                topBar.getClock().pause();
                break;
            }
        }
    }

    public void update(List<KeyCode> inputList) {
        if (status == STATUS.PLAYING) {
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
                setStatus(STATUS.LOSE);
            }

            // Handle next level
            levelMap.update();

            Bomber player = entitiesManager.bombers.get(0);
            filter.update(player.getX() + player.getWidth() / 2.0 - camera.getX(),
                player.getY() + player.getHeight() / 2.0 - camera.getY());
        }
    }

    public void render() {
        if (status == STATUS.PLAYING) {
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
        pauseMenu.zoom();
    }
}
