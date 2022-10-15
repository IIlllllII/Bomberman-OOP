package uet.oop.bomberman.core.stages;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.bomb.Flame;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;

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
            Image background = new Image(getClass().getResource("/intro.jpg").toURI().toString());
            ImageView imageView = new ImageView(background);
            imageView.setFitWidth(GameConfig.WIDTH);
            imageView.setFitHeight(GameConfig.HEIGHT);
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
            progressBar.setPrefWidth(300);
            progressBar.setLayoutX(GameConfig.WIDTH / 2.0 - 150);
            progressBar.setLayoutY(GameConfig.HEIGHT * 8 / 9.0);
            progressBar.setEffect(new Bloom() {
                {
                    setInput(new Reflection());
                }
            });

            final Transition progressAnimation = new Transition() {
                {
                    setCycleDuration(Duration.millis(4000));
                }

                @Override
                protected void interpolate(double frac) {
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

        scene = new Scene(root, GameConfig.WIDTH, GameConfig.HEIGHT,
                Color.rgb(4, 219, 251));

        (new Thread(() -> {
            SpriteSheet.init();
            LevelMap.init();
            Bomb.init();
            Flame.init();
//            MusicPlayer.init();
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
