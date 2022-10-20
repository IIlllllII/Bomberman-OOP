package uet.oop.bomberman.components.entities.items.item_types;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.stages.GameStage;

public class Coin extends Item {
    private final Animation animation;
    private final Animation virtualTimer;
    private final int score = 100;

    //private int countTimeToDisplay =

    public Coin(double x, double y) {
        super(x, y, coinImage);
        animation = new Animation(coinImage, 4, 4, 800, 0, 0, 16f, 17f,
                16f * 1.4f, 17f * 1.4f, 5f, false);
        virtualTimer = new Animation(coinImage, 1, 1, 1000, 0, 0, 0, 0);
        animation.setLoop(true);
        appear = true;
    }

    @Override
    public void update() {
        if (appear) {
            animation.update();
            if (eaten) {
                GameStage.getInstance().plusTotalScore(score);
            }
        } else {
            virtualTimer.update();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (appear) {
            animation.render(gc, x + 5 - camera.getX(), y + 5 - camera.getY());
        }
        if (eaten && !virtualTimer.isDone()) {
            Text text = new Text();
            gc.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            gc.setFill(Color.SNOW);
            gc.fillText(" + " + score,
                    x - camera.getX() + 16,
                    y - camera.getY() + 20 - virtualTimer.getCalcTime() / 32);
            appear = false;
        }
    }

    @Override
    public void changePower() {
        //Nothing to do
    }
}
