package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import uet.oop.bomberman.core.stages.GameStage;
import uet.oop.bomberman.core.stages.IntroStage;

public class Main extends Application {
    private IntroStage introStage;
    private GameStage gameStage;
    private AnimationTimer loop;

    @Override
    public void start(Stage mainStage) {

        gameStage = null;
        introStage = new IntroStage(mainStage);

        loop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                introStage.render();
                introStage.setDone(true);  // just for debug
                if (introStage.isInitDone() && introStage.isDone()) {
                    mainStage.close();
                    loop.stop();
                    gameStage = GameStage.getInstance();
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