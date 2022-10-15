package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.stages.GameStage;
import uet.oop.bomberman.core.stages.IntroStage;

import java.net.URISyntaxException;

public class Main extends Application {
    private GameStage gameStage;
    private AnimationTimer loop;

    // Entry point.
    @Override
    public void start(Stage mainStage) {
        gameStage = null;
        IntroStage introStage = IntroStage.getInstance();

        try {
            Image icon = new Image(getClass().getResource(GameConfig.ICON_PATH).toURI().toString());
            mainStage.getIcons().add(icon);
        } catch (URISyntaxException e) {
            System.out.println("icon path");
        }

        mainStage.setResizable(true);
        mainStage.setTitle(GameConfig.NAME);
        mainStage.setScene(introStage.getScene());
        mainStage.centerOnScreen();
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
                    gameStage.run();
                }
            }
        };
        loop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}