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
    private int count;
    Timeline timeline;

    /**
     *
     * @param time in second
     */
    public CountDown(int time) {
        count = time;

        setText(Integer.toString(time));
        setTextFill(Color.WHITE);
        setFont(Font.font(24));

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), this);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(6);
        scaleTransition.setAutoReverse(true);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1),e -> {
            count--;
            setText(String.valueOf(count));
            if (count < 10) {
                setTextFill(Color.RED);
                if (count < 4 && scaleTransition.getStatus() != Animation.Status.RUNNING) {
                    scaleTransition.play();
                }
            }
        }));

        timeline.setCycleCount(time);
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
