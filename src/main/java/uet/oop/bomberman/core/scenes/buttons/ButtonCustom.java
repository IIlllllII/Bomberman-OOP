package uet.oop.bomberman.core.scenes.buttons;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ButtonCustom extends StackPane {
    private final Text text;

    /**
     * Button for menu
     * @param name name
     * @param color color when hover it
     * @param width the width of rectangle background
     * @param height the height of rectangle background
     */
    public ButtonCustom(String name, Color color, double width, double height) {
        text = new Text(name);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        text.setFill(Color.WHITE);

        Rectangle bg = new Rectangle(width, height);
        bg.setOpacity(0.6);
        bg.setFill(Color.BLACK);
        bg.setEffect(new GaussianBlur((3.5)));

        if (name.equals("New Game")) {
            bg.setOpacity(0.7);
        }

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);

        setOnMouseEntered(mouseEvent -> {
            setScaleX(1.2);
            setScaleY(1.2);
            bg.setFill(color);
        });

        setOnMouseExited(mouseEvent -> {
            setScaleX(1);
            setScaleY(1);
            bg.setFill(Color.BLACK);
        });

        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());

        setOnMousePressed(mouseEvent -> setEffect(drop));
        setOnMouseReleased(mouseEvent -> setEffect(null));
    }

    public ButtonCustom(String name, Color before, Color after, double width, double height) {
        text = new Text(name);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        text.setFill(Color.WHITE);

        Rectangle bg = new Rectangle(width, height);
        bg.setOpacity(0.6);
        bg.setFill(before);

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);

        setOnMouseEntered(mouseEvent -> {
            setScaleX(1.2);
            setScaleY(1.2);
            bg.setFill(after);
        });

        setOnMouseExited(mouseEvent -> {
            setScaleX(1);
            setScaleY(1);
            bg.setFill(before);
        });

        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());

        setOnMousePressed(mouseEvent -> setEffect(drop));
        setOnMouseReleased(mouseEvent -> setEffect(null));
    }
}
