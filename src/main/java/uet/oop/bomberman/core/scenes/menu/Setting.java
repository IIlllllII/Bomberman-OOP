package uet.oop.bomberman.core.scenes.menu;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.core.sound.Music;

public class Setting extends VBox {
    public Setting(double prefWidth, double prefHeight) {
        setAlignment(Pos.CENTER);
        setSpacing(8);

        Text title = new Text("Setting (better UI coming soon)");
        title.setFont(new Font("/font1.ttf", 18));
        title.setStyle("-fx-font-weight: bold");
        title.setFill(Color.HOTPINK);

        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: rgba(128, 128, 128, 1)");
        tabPane.setMaxWidth(prefWidth);
        tabPane.setMaxHeight(prefHeight);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Music Tab
        Tab musicTab = new Tab("Music");
        GridPane musicContent = new GridPane();
        musicContent.setAlignment(Pos.CENTER);
        musicContent.setVgap(25);
        musicContent.setHgap(20);
        musicTab.setContent(musicContent);

        Text mute = new Text("Mute All");
        CheckBox isMute = new CheckBox();
        isMute.setOnMouseClicked(event -> {
            Music.setMuteAll(isMute.isSelected());
        });
        musicContent.add(mute, 0, 0);
        musicContent.add(isMute, 1, 0);

        Text volume = new Text("Volume");
        Slider sliderVolume = new Slider();
        sliderVolume.setOrientation(Orientation.HORIZONTAL);
        musicContent.add(volume, 0, 1);
        musicContent.add(sliderVolume, 1,1);

        Text theme = new Text("Theme Music");
        ChoiceBox<String> choiceTheme = new ChoiceBox<>();
        musicContent.add(theme, 0, 2);
        musicContent.add(choiceTheme, 1, 2);

        // Graphics Tab
        Tab graphicsTab = new Tab("Graphics");
        GridPane graphicsContent = new GridPane();
        graphicsContent.setAlignment(Pos.CENTER);
        graphicsTab.setContent(graphicsContent);

        Text zoom = new Text("Zoom");
        Slider sliderZoom = new Slider();
        sliderZoom.setOrientation(Orientation.HORIZONTAL);
        graphicsContent.add(zoom, 0,0);
        graphicsContent.add(sliderZoom, 1, 0);

        // Controls Tab
        Tab controlsTab = new Tab("Controls");

        tabPane.getTabs().addAll(musicTab, graphicsTab, controlsTab);
        getChildren().addAll(title, tabPane);
    }

}
