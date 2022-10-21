package uet.oop.bomberman.core.scenes.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class CountDown extends Label {
    public static final int DEFAULT_TIME = 180;
    private int time;
    Timeline timeline;

    public CountDown() {
        setTime(DEFAULT_TIME);
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
        setText(Integer.toString(time));
        setTextFill(Color.WHITE);
        setFont(Font.font(24));

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), this);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(6);
        scaleTransition.setAutoReverse(true);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1),e -> {
            this.time--;
            setText(String.valueOf(this.time));
            if (this.time < 10) {
                setTextFill(Color.RED);
                if (this.time < 4 && scaleTransition.getStatus() != Animation.Status.RUNNING) {
                    scaleTransition.play();
                }
            }
        }));

        timeline.setCycleCount(time);
    }
}
