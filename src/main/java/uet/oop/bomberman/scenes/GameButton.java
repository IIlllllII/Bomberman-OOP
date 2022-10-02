package uet.oop.bomberman.scenes;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

class GameButton {

}

class MenuButton extends StackPane {
    private final Text text;

    public MenuButton(String name) {
        text = new Text(name);
        text.setFont(text.getFont().font(20));
        text.setFill(Color.WHITE);

        Rectangle bg = new Rectangle(200, 30);
        bg.setOpacity(0.6);
        bg.setFill(Color.BLACK);
        bg.setEffect(new GaussianBlur((3.5)));

        setAlignment(Pos.CENTER_LEFT);
        setRotate(-0.5);
        getChildren().addAll(bg, text);

        setOnMouseEntered(mouseEvent -> {
            bg.setTranslateX(10);
            text.setTranslateX(10);
            bg.setFill(Color.WHITE);
            text.setFill(Color.BLACK);
        });

        setOnMouseExited(mouseEvent -> {
            bg.setTranslateX(0);
            text.setTranslateX(0);
            bg.setFill(Color.BLACK);
            text.setFill(Color.WHITE);
        });

        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());

        setOnMousePressed(mouseEvent -> setEffect(drop));
        setOnMouseReleased(mouseEvent -> setEffect(null));
    }
}