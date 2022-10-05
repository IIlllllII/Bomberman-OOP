package uet.oop.bomberman.core.stages;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;

import java.net.URISyntaxException;

public class IntroStage {
    private final GraphicsContext gc;
    private Image logo;
    private Image bar;
    private static double value;
    private double delayValue;
    public static final double MAXVALUE = 444;
    private boolean done;
    private boolean initDone;

    public IntroStage(Stage stage) {
        double width = GameConfig.WIDTH;
        double height = GameConfig.HEIGHT;
        initDone = false;

        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        Group group = new Group();
        group.getChildren().add(canvas);
        Scene scene = new Scene(group, width, height);

        stage.setResizable(false);
        stage.setTitle(GameConfig.NAME);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();

        try {
            Image icon = new Image(getClass().getResource(GameConfig.ICON_PATH).toURI().toString());
            stage.getIcons().add(icon);
            logo = new Image(getClass().getResource("/LogoIntro.png").toURI().toString());
            bar = new Image(getClass().getResource("/something/bar3.png").toURI().toString());
            value = 0;
            delayValue = 0;
            done = false;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        (new Thread(() -> {
            LevelMap.init();
//            Bomb.init();
//            Item.init();
//            Balloom.init();
//            Doll.init();
//            Kondoria.init();
//            Minvo.init();
//            Oneal.init();
//            Ovapi.init();
//            Pass.init();
//            MusicPlayer.init();
            Bomber.init();
            initDone = true;
        })).start();
    }

    public void render() {
//        gc.clearRect(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
//        gc.setFill(Color.web("3c75d8"));
//        gc.fillRect(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
//        gc.drawImage(logo, 0, 0, GameConfig.WIDTH, GameConfig.HEIGHT / 2.0);
//        gc.drawImage(bar, 55, GameConfig.HEIGHT - 100);
//        gc.setFill(Color.WHITE);
//        gc.fillRect(20, GameConfig.HEIGHT - 100, value, 20);
//
//        if (value < 330) {
//            value += 3;
//        } else if (value < MAXVALUE) {
//            delayValue ++;
//            if (delayValue > 40 && delayValue < 180) {
//                if (delayValue % 2 == 0) {
//                    value += 2;
//                }
//            }
//            if (delayValue > 280){
//                value += 2;
//            }
//        } else {
//            value = MAXVALUE;
//            done = true;
//        }
    }

    public static void setValue(double v) {
        value = v;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isInitDone() {
        return initDone;
    }
}
