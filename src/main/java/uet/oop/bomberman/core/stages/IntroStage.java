package uet.oop.bomberman.core.stages;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.bomb.Flame;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;

import java.net.URISyntaxException;

public class IntroStage {
    private Image background;
    private Image loading;
    private static boolean done;
    private boolean initDone;

    public IntroStage(Stage stage) {
        double width = GameConfig.WIDTH;
        double height = GameConfig.HEIGHT;
        initDone = false;
        done = false;

        try {
            Image icon = new Image(getClass().getResource(GameConfig.ICON_PATH).toURI().toString());
            stage.getIcons().add(icon);
            background = new Image(getClass().getResource("/greeting.jpg").toURI().toString());
            loading = new Image(getClass().getResource("/intro.png").toURI().toString());
        } catch (URISyntaxException e) {
            System.out.println("Intro stage");
            e.printStackTrace();
        }

        final ImageView viewBackground = new ImageView(background);
        viewBackground.setFitWidth(GameConfig.WIDTH);
        viewBackground.setFitHeight(GameConfig.HEIGHT);

        final ImageView viewLoading = new ImageView(loading);
        viewLoading.setLayoutX(10);
        viewLoading.setLayoutY(GameConfig.HEIGHT - 74 - 15);
        final Animation animation = new SpriteAnimation(viewLoading, Duration.millis(5000),
                18, 6,
                0, 0,
                74, 74
        );
        animation.play();
        Group root = new Group();

        root.getChildren().addAll(viewBackground, viewLoading);
        Scene scene = new Scene(root, width, height);

        stage.setResizable(false);
        stage.setTitle(GameConfig.NAME);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();


        (new Thread(() -> {
            SpriteSheet.init();
            LevelMap.init();
            Bomb.init();
            Flame.init();
            Bomber.init();
            initDone = true;
        })).start();
    }

    public boolean isDone() {
        return done;
    }

    public static void setDone(boolean done) {
        IntroStage.done = done;
    }

    public boolean isInitDone() {
        return initDone;
    }

    /**
     * Needs to set the duration of a single cycle.
     */
    private static class SpriteAnimation extends Transition {
        private final ImageView imageView;
        private final int count;
        private final int columns;
        private final int offsetX;
        private final int offsetY;
        private final int width;
        private final int height;
        private int lastIndex;

        /**
         *
         * @param imageView image.
         * @param duration : how long it should take to go through all frames
         * @param count : the number of frames
         * @param columns : the number of the columns
         * @param offsetX : offset of the first frame
         * @param offsetY :
         * @param width :
         * @param height :
         */
        public SpriteAnimation(ImageView imageView, Duration duration,
                               int count,   int columns,
                               int offsetX, int offsetY,
                               int width,   int height) {
            this.imageView = imageView;
            this.count     = count;
            this.columns   = columns;
            this.offsetX   = offsetX;
            this.offsetY   = offsetY;
            this.width     = width;
            this.height    = height;
            setCycleDuration(duration);
            setInterpolator(Interpolator.LINEAR);
        }

        /**
         * called in each frame, while the Transition is running.
         * @param k
         * The relative position
         */
        @Override
        protected void interpolate(double k) {
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                final int x = (index % columns) * width  + offsetX;
                final int y = (index / columns) * height + offsetY;
                imageView.setViewport(new Rectangle2D(x, y, width, height));
                lastIndex = index;
            } else if (index == count - 1){
                setDone(true);
            }
        }
    }
}
