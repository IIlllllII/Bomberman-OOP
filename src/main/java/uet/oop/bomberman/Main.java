package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import uet.oop.bomberman.core.GameManager;
import uet.oop.bomberman.core.IntroGame;

public class Main extends Application {
    private IntroGame introGame;
    private GameManager gameManager;
    private AnimationTimer loop;

    @Override
    public void start(Stage mainStage) {

        gameManager = null;
        introGame = new IntroGame(mainStage);

        loop = new AnimationTimer() {
            @Override
            public void handle(long l) {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                introGame.draw();
////                introGame.setDone(true);
                if (introGame.isInitDone() && introGame.isDone()) {
                    mainStage.close();
                    loop.stop();
                    gameManager = new GameManager();
                }
            }
        };
        loop.start();

    }
    public static void main(String[] args) {
        launch(args);
    }
}