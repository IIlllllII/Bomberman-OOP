package uet.oop.bomberman.core.scenes.menu;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.Main;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.sound.Sound;

public class Setting extends VBox {
    public Setting(double prefWidth, double prefHeight) {
        setAlignment(Pos.CENTER);
        setSpacing(8);

        Text title = new Text("Setting");
        title.setFont(new Font("/font1.ttf", 22));
        title.setStyle("-fx-font-weight: bold");
        title.setFill(Color.WHITE);

        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: rgba(128, 128, 128, 1); -fx-open-tab-animation: grow");
        tabPane.setMaxWidth(prefWidth);
        tabPane.setMaxHeight(prefHeight);
        tabPane.setTabMaxWidth(prefWidth / 3);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Music Tab
        Tab musicTab = new Tab("Music");
        GridPane musicContent = new GridPane();
        musicContent.setAlignment(Pos.CENTER);
        musicContent.setVgap(20);
        musicContent.setHgap(20);
        musicContent.setPadding(new Insets(5, 0, 5, 0));
        musicTab.setContent(musicContent);

        Text mute = new Text("Mute All");
        CheckBox isMute = new CheckBox();
        isMute.setOnMouseClicked(event -> {
            System.out.println(isMute.isSelected());
            Sound.setMuteAll(isMute.isSelected());
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
        {
            sliderZoom.setOrientation(Orientation.HORIZONTAL);
            sliderZoom.setValue(GameConfig.ZOOM);
            sliderZoom.setMin(0.75);
            sliderZoom.setMax(1.5);
            sliderZoom.setBlockIncrement(0.25);
            sliderZoom.setMajorTickUnit(0.25);
            sliderZoom.setMinorTickCount(0);
            sliderZoom.setShowTickLabels(true);
            sliderZoom.setShowTickMarks(true);
            sliderZoom.setOnMouseClicked(mouseEvent -> {
                GameConfig.ZOOM = Math.round(sliderZoom.getValue() * 4) / 4.0;
                sliderZoom.setValue(GameConfig.ZOOM);
                Main.zoom();
            });
        }
        graphicsContent.add(zoom, 0,0);
        graphicsContent.add(sliderZoom, 1, 0);

        // Controls Tab
        Tab controlsTab = new Tab("Controls");

        tabPane.getTabs().addAll(musicTab, graphicsTab, controlsTab);
        getChildren().addAll(title, tabPane);
    }

}
