package uet.oop.bomberman.core.scenes.game;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TopBar extends HBox {
    private final Clock clock;
    private int score;
    private final Label scoreLabel;

    private static class SingletonHelper {
        private static final TopBar INSTANCE = new TopBar();
    }

    public static TopBar getInstance() {
        return TopBar.SingletonHelper.INSTANCE;
    }

    private TopBar() {
        setSpacing(50);
        setMaxHeight(32);
        setAlignment(Pos.CENTER);
        setSpacing(200);

        clock = new Clock();
        score = 0;
        scoreLabel = new Label(String.format("SCORE: %06d", score));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font(24));

        getChildren().addAll(clock, scoreLabel);
    }

    public void reset() {
        score = 0;
        clock.setTime(Clock.DEFAULT_TIME);
    }

    public void setClock(int time) {
        clock.setTime(time);
    }

    public Clock getClock() {
        return clock;
    }

    public void addScore(int amount) {
        score += amount;
        scoreLabel.setText(String.format("SCORE: %06d", score));
    }
}
