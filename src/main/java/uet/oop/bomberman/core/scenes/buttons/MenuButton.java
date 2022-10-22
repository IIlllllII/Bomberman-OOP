package uet.oop.bomberman.core.scenes.buttons;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuButton extends StackPane {
    private final Text text;

    /**
     * Button for menu.
     * @param name title for button
     * @param color color when hover it
     */
    public MenuButton(String name, Color color) {
        text = new Text(name);
        text.setFont(new Font("/font1.ttf", 20));
        text.setStyle("-fx-font-weight: bold");
        text.setFill(Color.WHITE);

        Rectangle bg = new Rectangle(180, 35);
        bg.setOpacity(0.6);
        bg.setFill(Color.BLACK);
        bg.setEffect(new GaussianBlur((3.5)));

        if (name.equals("New Game")) {
            setScaleX(1.2);
            setScaleY(1.2);
            bg.setOpacity(0.7);
        }

        setAlignment(Pos.CENTER);
        setRotate(-0.5);
        getChildren().addAll(bg, text);

        setOnMouseEntered(mouseEvent -> {
            if (name.equals("New Game")) {
                setScaleX(1.4);
                setScaleY(1.4);
            } else {
                setScaleX(1.1);
                setScaleY(1.1);
            }
            bg.setFill(color);
        });

        setOnMouseExited(mouseEvent -> {
            if (name.equals("New Game")) {
                setScaleX(1.2);
                setScaleY(1.2);
            } else {
                setScaleX(1);
                setScaleY(1);
            }
            bg.setFill(Color.BLACK);
        });

        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());

        setOnMousePressed(mouseEvent -> setEffect(drop));
        setOnMouseReleased(mouseEvent -> setEffect(null));
    }
}
