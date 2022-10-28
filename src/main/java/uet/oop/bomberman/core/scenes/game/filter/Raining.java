package uet.oop.bomberman.core.scenes.game.filter;

import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import uet.oop.bomberman.config.GameConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Raining extends Group {
    private final List<Rectangle> seeds;

    private static class SingletonHelper {
        private static final Raining INSTANCE = new Raining();
    }

    public static Raining getInstance() {
        return Raining.SingletonHelper.INSTANCE;
    }
    private Raining() {
        Random r = new Random();
        seeds = new ArrayList<>();
        List<Integer> speed = new ArrayList<>();
        for (int i = 0; i < 250; i++) {
            speed.add(r.nextInt(3) + 2);
            int x = r.nextInt(GameConfig.WIDTH);
            int y = r.nextInt(GameConfig.HEIGHT) - 50;
            seeds.add(new Rectangle(x, y, 2, r.nextInt(3) + 5));
            seeds.get(i).setFill(Color.rgb(150,211,255,0.6));
        }
        getChildren().addAll(seeds);

        System.out.println(seeds.get(0).getY());
        System.out.println(seeds.get(0).getLayoutY());

        Transition transition = new Transition() {
            {
                setCycleDuration(Duration.INDEFINITE);
            }
            @Override
            protected void interpolate(double frag) {
                for (int i = 0; i < seeds.size(); i++) {
                    double y = seeds.get(i).getY();
                    y += speed.get(i);
                    if (y > GameConfig.HEIGHT + 10) {
                        y = -5;
                        int x = r.nextInt(GameConfig.WIDTH);
                        seeds.get(i).setX(x);
                    }
                    seeds.get(i).setY(y);
                }
            }
        };
        transition.play();
    }

    public void setColor(Color color) {
        for (int i = 0; i < 250; i++) {
            seeds.get(i).setFill(color);
        }
    }
}
