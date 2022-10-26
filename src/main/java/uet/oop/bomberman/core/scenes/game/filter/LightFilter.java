package uet.oop.bomberman.core.scenes.game.filter;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.scenes.game.BottomBar;

public class LightFilter {
    public enum STATUS {
        DAY, DUSK, NIGHT, DAWN
    }
    private STATUS status = STATUS.DAY;
    private final int width = GameConfig.WIDTH;
    private final Rectangle filter;
    private double opacity = 0.0;
    private int dayCounter = 0;

    public LightFilter(int circleRadius) {
        filter = new Rectangle(width * 2, width * 2);

        Color[] darkColor = new Color[5];

        darkColor[0] = new Color(0, 0, 0, 0.0f);
        darkColor[1] = new Color(0, 0, 0, 0.25f);
        darkColor[2] = new Color(0, 0, 0, 0.5f);
        darkColor[3] = new Color(0, 0, 0, 0.75f);
        darkColor[4] = new Color(0, 0, 0, 0.98);

        filter.setLayoutX(-width / 2.0);
        filter.setLayoutY(-width / 2.0);

        RadialGradient color = new RadialGradient(0, 0, .5, .5,
                circleRadius / (width * 2.0), true,
                CycleMethod.NO_CYCLE,
                new Stop(0.0, darkColor[0]), new Stop(0.25, darkColor[1]),
                new Stop(0.5, darkColor[2]), new Stop(0.75, darkColor[3]),
                new Stop(1.0, darkColor[4]));

        filter.setFill(color);
        filter.setOpacity(opacity);
    }

    public void update(double centerX, double centerY) {
        filter.setLayoutX(centerX - width);
        filter.setLayoutY(centerY - width);

        switch (status) {
            case DAY: {
                dayCounter++;
                if (dayCounter > 1000) {
                    status = STATUS.DUSK;
                    dayCounter = 0;
                }
                break;
            }
            case DUSK: {
                opacity += 0.0004f;
                if (opacity > 1f) {
                    opacity = 1;
                    status = STATUS.NIGHT;
                }
                filter.setOpacity(opacity);
                break;
            }
            case NIGHT: {
                dayCounter++;
                if (dayCounter > 1300) {
                    status = STATUS.DAWN;
                    dayCounter = 0;
                }
                break;
            }
            case DAWN: {
                opacity -= 0.0005;
                if (opacity < 0f) {
                    opacity = 0;
                    status = STATUS.DAY;
                }
                filter.setOpacity(opacity);
                break;
            }
        }

        BottomBar.getInstance().setDay(status.toString());
    }

    public void reset() {
        opacity = 0.0;
        dayCounter = 0;
        filter.setOpacity(opacity);
        status = STATUS.DAY;
    }

    public Node getFilter() {
        return filter;
    }
}
