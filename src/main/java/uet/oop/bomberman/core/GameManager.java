package uet.oop.bomberman.core;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.config.GameConfig;

public class GameManager extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Text text = new Text();
        text.setText("Hello");
        text.setX(250);
        text.setY(150);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));

        Group root = new Group(text);

        Scene scene = new Scene(root, GameConfig.WIDTH, GameConfig.HEIGHT);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        stage.setResizable(false);
        stage.setTitle(GameConfig.NAME);
        //stage.getIcons().add(new Image("../../../../resources/images/" + GameConfig.ICON_NAME));
        stage.setScene(scene);

        stage.show();
    }
}
