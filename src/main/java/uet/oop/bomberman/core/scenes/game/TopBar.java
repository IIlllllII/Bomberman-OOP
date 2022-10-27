package uet.oop.bomberman.core.scenes.game;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.bomber.Bomber;
import uet.oop.bomberman.core.scenes.PlayScene;
import uet.oop.bomberman.core.scenes.buttons.ButtonCustom;

import java.net.URISyntaxException;

public class TopBar extends HBox {
    private final Clocks clock;
    private int score;
    private int lives = Bomber.DEFAULT_LIVES;
    private final Label scoreLabel;
    private final Label livesLabel;

    private static class SingletonHelper {
        private static final TopBar INSTANCE = new TopBar();
    }

    public static TopBar getInstance() {
        return TopBar.SingletonHelper.INSTANCE;
    }

    private TopBar() {
        setSpacing(50);
        setMaxHeight(40);
        setAlignment(Pos.CENTER);
        setSpacing(120);

        try {
            Image pause = new Image(getClass().getResource("/UI/button/pauseButton.png").toURI().toString());
            ButtonCustom pauseButton = new ButtonCustom(pause, 40, 40);
            pauseButton.setOnMouseClicked(mouseEvent -> {
                PlayScene.getInstance().setStatus(PlayScene.STATUS.PAUSE);
            });
            getChildren().add(pauseButton);
        } catch (URISyntaxException e) {
            System.out.println("pause top bar");
        }

        clock = new Clocks();
        score = 0;
        scoreLabel = new Label(String.format("SCORE: %06d", score));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        getChildren().addAll(clock, scoreLabel);

        try {
            ImageView imageView = new ImageView(new Image(getClass().getResource("/UI/heart.png").toURI().toString()));
            imageView.setFitWidth(30);
            imageView.setFitHeight(25);
            livesLabel = new Label(" x " + lives, imageView);
            livesLabel.setTextFill(Color.WHITE);
            livesLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            getChildren().addAll(livesLabel);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void reset() {
        score = 0;
        scoreLabel.setText(String.format("SCORE: %06d", score));
        clock.setTime(Clocks.DEFAULT_TIME);
    }

    public void setClock(int time) {
        clock.setTime(time);
    }

    public Clocks getClock() {
        return clock;
    }

    public void addScore(int amount) {
        score += amount;
        scoreLabel.setText(String.format("SCORE: %06d", score));
    }

    public int getScore() {
        return score;
    }

    public void setLives(int lives) {
        this.lives = lives;
        livesLabel.setText(" x " + lives);
    }
}
