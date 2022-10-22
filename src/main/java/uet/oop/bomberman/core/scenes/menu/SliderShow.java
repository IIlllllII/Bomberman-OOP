package uet.oop.bomberman.core.scenes.menu;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class SliderShow extends AnchorPane {
    private static FadeTransition transition;
    private final StackPane stackPane = new StackPane();
    private final AnchorPane backPane = new AnchorPane();
    private final AnchorPane frontPane = new AnchorPane();
    private static boolean initialized = false;
    private static ObservableList<Image> backImages;
    private static ObservableList<Image> frontImages;
    private int backIndex = 0;
    private int frontIndex = 0;

    public static void init() {
        if (!initialized) {
            frontImages = FXCollections.observableArrayList();
            backImages = FXCollections.observableArrayList();
            try {
                frontImages.add(new Image(SliderShow.class.getResource("/background/b0.jpg").toURI().toString()));
                frontImages.add(new Image(SliderShow.class.getResource("/background/b2.jpg").toURI().toString()));
                frontImages.add(new Image(SliderShow.class.getResource("/background/b4.jpg").toURI().toString()));
                frontImages.add(new Image(SliderShow.class.getResource("/background/b6.jpg").toURI().toString()));
                frontImages.add(new Image(SliderShow.class.getResource("/background/b8.jpg").toURI().toString()));
                frontImages.add(new Image(SliderShow.class.getResource("/background/b10.jpg").toURI().toString()));


                backImages.add(new Image(SliderShow.class.getResource("/background/b1.png").toURI().toString()));
                backImages.add(new Image(SliderShow.class.getResource("/background/b3.jpg").toURI().toString()));
                backImages.add(new Image(SliderShow.class.getResource("/background/b5.jpg").toURI().toString()));
                backImages.add(new Image(SliderShow.class.getResource("/background/b7.jpg").toURI().toString()));
                backImages.add(new Image(SliderShow.class.getResource("/background/b9.jpg").toURI().toString()));
                backImages.add(new Image(SliderShow.class.getResource("/background/b11.jpg").toURI().toString()));

            } catch (URISyntaxException e) {
                System.out.println("slide show");
                throw new RuntimeException(e);
            }
            initialized = true;
        }
    }

    /**
     * A Slider Show
     * @param animationDelay: Fade Animation in second
     * @param visibilityDelay Pause Animation in second
     */
    public SliderShow(int animationDelay, int visibilityDelay) {
        super();

        setBackgroundImage(backPane, backImages.get(0));
        setBackgroundImage(frontPane, frontImages.get(0));

        initialized();
        initSliderShow(animationDelay, visibilityDelay);
        reset();
    }

    private void initialized() {
        this.stackPane.getChildren().addAll(backPane, frontPane);
        frontPane.toFront();
        frontPane.setOpacity(0);
        backPane.toBack();
        // Set anchorPane fill entire parent
        setAnchor(stackPane);
        getChildren().add(stackPane);
    }

    private void initSliderShow(int animationDelay, int visibilityDelay) {
        transition = new FadeTransition(Duration.seconds(animationDelay), frontPane);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);

        Runnable runnable = () -> Platform.runLater(() ->
                frontPane.opacityProperty().addListener((observable, oldValue, newValue) -> {
//                    System.out.println(newValue);
            PauseTransition pause;
            if (newValue.doubleValue() == 0) {
                frontIndex++;
                if (frontIndex == frontImages.size()) {
                    frontIndex = 0;
                }
                setBackgroundImage(frontPane, frontImages.get(frontIndex));
                pause = new PauseTransition(Duration.seconds(visibilityDelay));
                pause.setOnFinished(event -> transition.play());
                transition.pause();
                pause.play();
            } else if (newValue.doubleValue() == 1) {
                backIndex++;
                if (backIndex == backImages.size()) {
                    backIndex = 0;
                }
                setBackgroundImage(backPane, backImages.get(backIndex));
                pause = new PauseTransition(Duration.seconds(visibilityDelay));
                pause.setOnFinished(event -> transition.play());
                transition.pause();
                pause.play();
            }
        }));
        new Thread(runnable).start();
    }

    public void stop() {
        if (transition != null) {
            transition.stop();
            transition.setCycleCount(0);
        }
    }

    public void reset() {
        if (transition.getStatus() != Animation.Status.RUNNING) {
            transition.setCycleCount(Animation.INDEFINITE);
            transition.setAutoReverse(true);
            transition.play();
        }
    }

    private static void setAnchor(Node node) {
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
    }

    private void setBackgroundImage(AnchorPane target, Image image) {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true,true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        target.setBackground(background);
    }
}
