package uet.oop.bomberman.core.stages;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.bomb.Flame;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.HighScore;
import uet.oop.bomberman.core.scenes.MenuScene;

import java.net.URISyntaxException;

public class IntroStage {
    private boolean loadDone;
    private boolean initDone;
    private final Scene scene;

    private static class SingletonHelper {
        private static final IntroStage INSTANCE = new IntroStage();
    }
    public static IntroStage getInstance() {
        return IntroStage.SingletonHelper.INSTANCE;
    }

    private IntroStage() {
        initDone = false;
        loadDone = false;

        Group root = new Group();
        try {
            Image background = new Image(getClass().getResource("/intro.png").toURI().toString());
            ImageView imageView = new ImageView(background);
            imageView.setFitWidth(GameConfig.SCENE_WIDTH);
            imageView.setFitHeight(GameConfig.SCENE_HEIGHT);
            root.getChildren().add(imageView);
        } catch (URISyntaxException e) {
            System.out.println("background intro");
            throw new RuntimeException(e);
        }

        // Fading
        final FadeTransition fading = new FadeTransition();
        {
            fading.setDuration(Duration.millis(1000));
            fading.setFromValue(1);
            fading.setToValue(0);
            fading.setNode(root);
            fading.setOnFinished(event -> loadDone = true);
        }

        // progress bar
        final ProgressBar progressBar = new ProgressBar(0);
        {
            progressBar.setPrefWidth(GameConfig.SCENE_WIDTH + 10);
            progressBar.setLayoutX(0);
            progressBar.setLayoutY(GameConfig.SCENE_HEIGHT * 7 / 8.0);
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setInput(new Reflection());
            progressBar.setEffect(colorAdjust);

            final Transition progressAnimation = new Transition() {
                {
                    setCycleDuration(Duration.millis(5000));
                }

                @Override
                protected void interpolate(double frac) {
                    colorAdjust.setHue(0.25 - frac / 2);
                    progressBar.setProgress(frac);
                }
            };
            progressAnimation.play();
            progressAnimation.setOnFinished(event -> {
                progressBar.setVisible(false);
                fading.play();
            });
        }

        root.getChildren().add(progressBar);

        scene = new Scene(root, GameConfig.SCENE_WIDTH, GameConfig.SCENE_HEIGHT);

        (new Thread(() -> {
            MenuScene.init();
            HighScore.init();
            SpriteSheet.init();
            LevelMap.init();
            Bomb.init();
            Flame.init();
            Bomber.init();
            initDone = true;
        })).start();
    }

    public boolean isDone() {
        return loadDone && initDone;
    }

    public void setLoadDone(boolean loadDone) {
        this.loadDone = loadDone;
    }

    public Scene getScene() {
        return scene;
    }
}
