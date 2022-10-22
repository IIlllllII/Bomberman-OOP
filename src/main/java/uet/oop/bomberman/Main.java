package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.scenes.SceneManager;
import uet.oop.bomberman.core.stages.GameStage;
import uet.oop.bomberman.core.stages.IntroStage;

import java.net.URISyntaxException;

public class Main extends Application {
    private GameStage gameStage;
    private AnimationTimer loop;
    private static Stage mainStage;

    // Entry point.
    @Override
    public void start(Stage mainStage) {
        Main.mainStage = mainStage;
        gameStage = null;
        IntroStage introStage = IntroStage.getInstance();

        try {
            Image icon = new Image(getClass().getResource(GameConfig.ICON_PATH).toURI().toString());
            mainStage.getIcons().add(icon);
        } catch (URISyntaxException e) {
            System.out.println("icon path");
        }

        mainStage.setScene(introStage.getScene());
        mainStage.setResizable(false);
        mainStage.setTitle(GameConfig.NAME);
        mainStage.centerOnScreen();
        mainStage.setAlwaysOnTop(true);
        mainStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                mainStage.setOpacity(0.5);
            } else {
                mainStage.setOpacity(1);
            }
        });
        mainStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        mainStage.show();

        loop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                introStage.setLoadDone(true);  // just for debug
                if (introStage.isDone()) {
                    loop.stop();
                    gameStage = GameStage.getInstance();
                    mainStage.setScene(gameStage.getScene());
                    zoom();
                    gameStage.run();
                }
            }
        };
        loop.start();
    }

    public static void zoom() {
        SceneManager.getInstance().zoom();
        mainStage.setWidth(GameConfig.SCENE_WIDTH * GameConfig.ZOOM);
        mainStage.setHeight(GameConfig.SCENE_HEIGHT * GameConfig.ZOOM + 37);
        mainStage.centerOnScreen();
    }
    public static void main(String[] args) {
        launch(args);
    }
}