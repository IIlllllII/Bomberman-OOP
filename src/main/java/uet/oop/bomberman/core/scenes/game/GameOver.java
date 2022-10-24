package uet.oop.bomberman.core.scenes.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.core.HighScore;
import uet.oop.bomberman.core.scenes.SceneManager;
import uet.oop.bomberman.core.scenes.buttons.ButtonCustom;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GameOver extends HBox {
    private final Text highScore;
    private final TextField textField;
    private ImageView imageView;
    private boolean lose;

    public GameOver() {
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: darkslateblue");
        setMaxHeight(280);
        setMaxWidth(480);
        setSpacing(20);

        SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        Text title = new Text("GAME OVER");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        title.setFill(Color.RED);
        title.setLineSpacing(3);

        highScore = new Text();
        highScore.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        highScore.setFill(Color.WHITE);

        Label label = new Label("Your Name: ");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        label.setTextFill(Color.WHITE);

        textField = new TextField();
        textField.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        textField.setAlignment(Pos.CENTER);
        textField.setDisable(true);
        textField.setMaxWidth(240);
        label.setLabelFor(textField);

        ButtonCustom save = new ButtonCustom("Save & Back to Menu", Color.DARKRED, Color.RED, 180, 35);
        save.setFont(18);
        save.setPadding(new Insets(20, 0, 0, 0));
        save.setOnMouseClicked(mouseEvent -> {
            Date date = new Date();
            HighScore.add(new HighScore(textField.getText(), TopBar.getInstance().getScore(),
                    LevelMap.getInstance().getLevel(), format.format(date)));
            HighScore.update();
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
        });

        ButtonCustom back = new ButtonCustom("Back to Menu", Color.CRIMSON, 150, 25);
        back.setFont(18);
        back.setOnMouseClicked(mouseEvent -> {
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
        });

        vBox.getChildren().addAll(title, highScore, label, textField, save, back);

        try {
            imageView = new ImageView(new Image(getClass().getResource("/UI/gameover2.jpg").toURI().toString()));
        } catch (URISyntaxException e) {
            System.out.println("game over image");
        }
        imageView.setFitHeight(280);
        imageView.setFitWidth(200);

        getChildren().addAll(vBox, imageView);

        reset();
    }

    public void reset() {
        lose = false;
        highScore.setText("Your Score: ");
        setVisible(false);
        textField.setText("");
        textField.setDisable(true);
    }

    public void setLose(boolean lose) {
        this.lose = lose;
        if (lose) {
            setVisible(true);
            textField.setDisable(false);
            highScore.setText("Your Score: " + TopBar.getInstance().getScore());
        }
    }

    public boolean isLose() {
        return lose;
    }
}
