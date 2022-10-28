package uet.oop.bomberman.core.scenes.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.HighScore;
import uet.oop.bomberman.core.scenes.SceneManager;
import uet.oop.bomberman.core.scenes.buttons.ButtonCustom;
import uet.oop.bomberman.core.scenes.menu.Leaderboards;
import uet.oop.bomberman.core.sound.Sound;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Win extends StackPane {
    private final Text highScore;
    private final TextField textField;

    public Win() {
        setAlignment(Pos.CENTER);
        setPrefHeight(GameConfig.HEIGHT);
        setPrefWidth(GameConfig.WIDTH);

        try {
            ImageView win = new ImageView(new Image(getClass().getResource("/UI/win.jpg").toURI().toString()));
            win.setFitHeight(GameConfig.HEIGHT);
            win.setFitWidth(GameConfig.WIDTH);
            win.setOpacity(0.8);
            getChildren().add(win);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        Label title = new Label("VICTORY");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        title.setTextFill(Color.YELLOW);
        title.setLineSpacing(3);
        title.setPadding(new Insets(0, 0, 45, 0));

        highScore = new Text();
        highScore.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        highScore.setFill(Color.BLACK);

        Label label = new Label("Your Name: ");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        label.setTextFill(Color.BLACK);

        textField = new TextField();
        textField.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        textField.setAlignment(Pos.CENTER);
        textField.setDisable(true);
        textField.setMaxWidth(240);
        label.setLabelFor(textField);

        ButtonCustom save = new ButtonCustom("Save & Back to Menu", Color.DARKRED, Color.RED, 250, 50);
        save.setFont(18);
        save.setPadding(new Insets(20, 0, 0, 0));
        save.setOnMouseClicked(mouseEvent -> {
            Date date = new Date();
            Leaderboards.add(new HighScore(textField.getText(), TopBar.getInstance().getScore(),
                    LevelMap.getInstance().getLevel(), format.format(date)));
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
            reset();
        });

        ButtonCustom back = new ButtonCustom("Back to Menu", Color.CRIMSON, 180, 35);
        back.setFont(18);
        back.setOnMouseClicked(mouseEvent -> {
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
            reset();
        });

        vBox.getChildren().addAll(title, highScore, label, textField, save, back);

        getChildren().add(vBox);

        reset();
    }

    private void reset() {
        highScore.setText("Your Score: ");
        textField.setText("");
        textField.setDisable(true);
        setVisible(false);
    }

    public void setEnable() {
        new Sound(Sound.WIN_GAME).play();
        highScore.setText("Your Score: " + TopBar.getInstance().getScore());
        setVisible(true);
        textField.setDisable(false);
    }
}
