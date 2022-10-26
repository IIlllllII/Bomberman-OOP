package uet.oop.bomberman.core.scenes.game;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.components.entities.bomber.Bomber;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.items.item_types.*;

import java.net.URISyntaxException;

public class BottomBar extends HBox {
    private final HBox itemLayout;
    private int live = Bomber.DEFAULT_LIVES;
    private int bomb = 1;
    private int flame = 1;
    private int speed = 1;
    private final LabelItem liveLabel;
    private final LabelItem bombLabel;
    private final LabelItem flameLabel;
    private final LabelItem speedLabel;

    private static class SingletonHelper {
        private static final BottomBar INSTANCE = new BottomBar();
    }

    public static BottomBar getInstance() {
        return BottomBar.SingletonHelper.INSTANCE;
    }

    private BottomBar() {
        setAlignment(Pos.CENTER);
        setSpacing(10);

        try {
            ImageView imageView = new ImageView(new Image(getClass().getResource("/UI/Image.jpg").toURI().toString()));
            imageView.setFitWidth(64);
            imageView.setFitHeight(64);
            getChildren().add(imageView);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        itemLayout = new HBox(10);
        itemLayout.setAlignment(Pos.CENTER);
        itemLayout.setMaxHeight(32);
        itemLayout.setStyle("-fx-background-color: black; -fx-border-color: blue;");

        liveLabel = new LabelItem(Item.livesUp, live);
        bombLabel = new LabelItem(Item.bombUp, bomb);
        flameLabel = new LabelItem(Item.fireUp, flame);
        speedLabel = new LabelItem(Item.speedUp, speed);

        itemLayout.getChildren().addAll(liveLabel, bombLabel, flameLabel, speedLabel);

        getChildren().add(itemLayout);
    }

    public void add(Item item) {
        if (item instanceof BombUp) {
            bombLabel.setNumber(++bomb);
        } else if (item instanceof LivesUp) {
            liveLabel.setNumber(++live);
        } else if (item instanceof SpeedUp) {
            speedLabel.setNumber(++speed);
        } else if (item instanceof FlameUp) {
            flameLabel.setNumber(++flame);
        } else {
            itemLayout.getChildren().add(new LabelItem(item.getImage(), 1,
                    item.getTimePowerUp() / 1000));
        }
    }

    public void remove(Item item) {
        itemLayout.getChildren().removeIf(i -> {
            LabelItem temp = (LabelItem) i;
            return temp.image.equals(item.getImage());
        });
    }

    public void setLive(int live) {
        this.live = live;
        liveLabel.setNumber(live);
    }

    public void reset() {
        live = Bomber.DEFAULT_LIVES;
        bomb = 1;
        flame = 1;
        speed = 1;
        liveLabel.setNumber(3);
        bombLabel.setNumber(1);
        flameLabel.setNumber(1);
        speedLabel.setNumber(1);

        itemLayout.getChildren().clear();
        itemLayout.getChildren().addAll(liveLabel, bombLabel, flameLabel, speedLabel);
    }

    public void resetNextLevel() {
        itemLayout.getChildren().clear();
        itemLayout.getChildren().addAll(liveLabel, bombLabel, flameLabel, speedLabel);
    }

    private class LabelItem extends StackPane {
        private Text number;
        private final Image image;

        public LabelItem(Image image, int count) {
            setAlignment(Pos.BOTTOM_RIGHT);
            setMaxSize(32, 32);

            number = new Text(String.valueOf(count));
            number.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            number.setFill(Color.SNOW);

            this.image = image;

            getChildren().addAll(new ImageView(image), number);
        }

        public LabelItem(Image image, int count, double timeLeft) {
            setAlignment(Pos.CENTER);
            setMaxSize(32, 32);

            Clocks clocks = new Clocks((int) timeLeft, 12);
            clocks.setFont(Font.font("Verdana", FontWeight.BOLD, 13));

            this.image = image;

            getChildren().addAll(new ImageView(image), clocks);
        }

        public void setNumber(int count) {
            number.setText(String.valueOf(count));
        }
    }
}
