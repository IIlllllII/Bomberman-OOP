package uet.oop.bomberman.core.scenes.game.filter;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import uet.oop.bomberman.config.GameConfig;

import java.util.ArrayList;

public class NightMode {
    private ArrayList<Shape> filter;
    private final double offsetX = -GameConfig.WIDTH / 2.0;
    private final double offsetY = -GameConfig.HEIGHT / 2.0;
    private int radius;

    public NightMode(int circleRadius) {
        filter = new ArrayList<>();
        radius = circleRadius;
        Color[] darkColor = new Color[5];

        darkColor[0] = new Color(0, 0, 0, 0.0f);
        darkColor[1] = new Color(0, 0, 0, 0.25f);
        darkColor[2] = new Color(0, 0, 0, 0.5f);
        darkColor[3] = new Color(0, 0, 0, 0.75f);
        darkColor[4] = new Color(0, 0, 0, 0.98);

        Rectangle darkArea = new Rectangle(0, 0, GameConfig.WIDTH * 2, GameConfig.HEIGHT * 2);
        Circle lightArea = new Circle(GameConfig.WIDTH, GameConfig.HEIGHT, circleRadius);
        filter.add(Shape.subtract(darkArea, lightArea));
        filter.get(0).setLayoutX(offsetX);
        filter.get(0).setLayoutY(offsetY);

        lightArea.setRadius(circleRadius + 1);
        filter.add(lightArea);
        filter.get(1).setLayoutX(GameConfig.WIDTH - circleRadius);
        filter.get(1).setLayoutY(GameConfig.HEIGHT - circleRadius);

        RadialGradient color = new RadialGradient(0, 0, .5, .5,
                .5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0.0, darkColor[0]), new Stop(0.25, darkColor[1]),
                new Stop(0.5, darkColor[2]), new Stop(0.75, darkColor[3]),
                new Stop(1.0, darkColor[4]));

        filter.get(0).setFill(darkColor[4]);
        filter.get(1).setFill(color);

    }

    public void update(double centerX, double centerY) {
        filter.get(0).setLayoutX(offsetX + centerX + offsetX);
        filter.get(0).setLayoutY(offsetY + centerY + offsetY);
        filter.get(1).setLayoutX(offsetX + centerX + offsetX);
        filter.get(1).setLayoutY(offsetY + centerY + offsetY);
    }

    public ArrayList<Shape> getFilter() {
        return filter;
    }
}
