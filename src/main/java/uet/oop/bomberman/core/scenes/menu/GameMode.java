package uet.oop.bomberman.core.scenes.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.components.maps.LevelMap;

import java.net.URISyntaxException;

public class GameMode extends VBox {
    private final double DEFAULT_WIDTH = 550;
    private final double DEFAULT_HEIGHT = 300;
    private Image autoPlayImage;
    private Image classicImage;

    public GameMode() {
        setAlignment(Pos.CENTER);
        setSpacing(15);

        try {
            autoPlayImage = new Image(this.getClass().getResource("/UI/GameMode/autoPlay.png").toURI().toString());
            classicImage = new Image(this.getClass().getResource("/UI/GameMode/classic.png").toURI().toString());
        } catch (URISyntaxException e) {
            System.out.println("game mode image");
        }

        String[] describe = {
                "Watch the AI play.",
                "Kill enemy and get high score.",
                "Coming soon."};

        Text title = new Text("GAME MODE");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        title.setFill(Color.WHITE);

        HBox modes = new HBox();
        modes.setAlignment(Pos.CENTER);
        modes.setSpacing(30);

        Mode autoPlay = new Mode(autoPlayImage, "Auto Play", describe[0]);
        Mode classic = new Mode(classicImage, "Classic", describe[1]);
        Mode training = new Mode(autoPlayImage, "Training", describe[2]);

        modes.getChildren().addAll(autoPlay, classic, training);

        classic.setOnClick();

        classic.setOnMouseClicked(mouseEvent -> {
            LevelMap.getInstance().setAuto(false);
            classic.setOnClick();
            autoPlay.reset();
            training.reset();
        });

        autoPlay.setOnMouseClicked(mouseEvent -> {
            LevelMap.getInstance().setAuto(true);
            autoPlay.setOnClick();
            classic.reset();
            training.reset();
        });

        training.setOnMouseClicked(mouseEvent -> {
            LevelMap.getInstance().setAuto(false);
            training.setOnClick();
            classic.reset();
            autoPlay.reset();
        });

        getChildren().addAll(title, modes);
    }

    private class Mode extends VBox {
        public Mode(Image image, String name, String describe) {
            reset();
            setAlignment(Pos.CENTER);
            setSpacing(5);

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(DEFAULT_WIDTH / 3 - 10);
            imageView.setFitHeight(DEFAULT_HEIGHT - 150);

            Text description = new Text(describe);
            description.setFill(Color.LIGHTYELLOW);

            Label title = new Label(name);
            title.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            title.setTextFill(Color.LIGHTSKYBLUE);

            reset();

            getChildren().addAll(imageView, description, title);
        }

        public void setOnClick() {
            setStyle("-fx-background-color: #65472F");
            setScaleX(1.1);
            setScaleY(1.1);
        }

        public void reset() {
            setStyle("-fx-background-color: darkslategray");
            setScaleX(1);
            setScaleY(1);
        }
    }
}
