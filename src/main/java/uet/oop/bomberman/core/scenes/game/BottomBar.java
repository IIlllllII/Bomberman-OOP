package uet.oop.bomberman.core.scenes.game;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.items.Item;

import java.net.URISyntaxException;
import java.util.List;

public class BottomBar extends HBox {
    private HBox item;
    private final List<Item> itemList;
    private int itemsEaten = 0;
    private static class SingletonHelper {
        private static final BottomBar INSTANCE = new BottomBar();
    }

    public static BottomBar getInstance() {
        return BottomBar.SingletonHelper.INSTANCE;
    }

    private BottomBar() {
        itemList = EntitiesManager.getInstance().items;

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

        item = new HBox(10);
        item.setAlignment(Pos.CENTER);
        item.setMaxHeight(32);
        item.setStyle("-fx-background-color: black; -fx-border-color: blue;");

        getChildren().add(item);
    }

    public void update() {

    }

    public void reset() {
        itemsEaten = 0;
    }
}
