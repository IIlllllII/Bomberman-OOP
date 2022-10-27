package uet.oop.bomberman.core.scenes.menu;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.Main;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.sound.BackgroundMusic;

import java.util.Objects;

public class Setting extends VBox {
    private final double DEFAULT_WIDTH = 400;
    private final double DEFAULT_HEIGHT = 200;
    private final CheckBox isMute;

    private static class SingletonHelper {
        private static final Setting INSTANCE = new Setting();
    }
    public static Setting getInstance() {
        return Setting.SingletonHelper.INSTANCE;
    }
    private Setting() {
        setAlignment(Pos.CENTER);
        setSpacing(8);

        Text title = new Text("Setting");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        title.setFill(Color.WHITE);

        // TAB PANE
        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: powderblue");
        tabPane.setMaxWidth(DEFAULT_WIDTH);
        tabPane.setMaxHeight(DEFAULT_HEIGHT);
        tabPane.setTabMaxWidth(DEFAULT_WIDTH / 3);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // MUSIC TAB
        Tab musicTab = new Tab("Music");
        GridPane musicContent = new GridPane();
        musicContent.setAlignment(Pos.CENTER);
        musicContent.setVgap(20);
        musicContent.setHgap(20);
        musicContent.setPadding(new Insets(5, 0, 5, 0));
        musicTab.setContent(musicContent);

        Text mute = new Text("Mute All");
        isMute = new CheckBox();
        isMute.setOnMouseClicked(event -> BackgroundMusic.setMute(isMute.isSelected()));
        musicContent.add(mute, 0, 0);
        musicContent.add(isMute, 1, 0);

        Text volume = new Text("Volume");
        Slider sliderVolume = new Slider();
        {
            BackgroundMusic backgroundMusic = BackgroundMusic.getInstance();
            sliderVolume.setOrientation(Orientation.HORIZONTAL);
            sliderVolume.setValue(backgroundMusic.getVolume() * 100);
            sliderVolume.setMin(0);
            sliderVolume.setMax(100);
            sliderVolume.setBlockIncrement(20);
            sliderVolume.setMajorTickUnit(20);
            sliderVolume.setMinorTickCount(1);
            sliderVolume.setShowTickLabels(true);
            sliderVolume.setShowTickMarks(true);
            sliderVolume.valueProperty().addListener(observable -> backgroundMusic.setVolume(sliderVolume.getValue() / 100));
        }
        musicContent.add(volume, 0, 1);
        musicContent.add(sliderVolume, 1,1);

        Text theme = new Text("Theme Music");
        ComboBox<String> choiceTheme = new ComboBox<>();
        choiceTheme.getItems().addAll("DEFAULT", "CUSTOMS");
        choiceTheme.setValue("CUSTOMS");
        choiceTheme.setOnAction(event -> {
            if (Objects.equals(choiceTheme.getValue(), BackgroundMusic.THEME.CUSTOMS.toString())) {
                BackgroundMusic.getInstance().setTheme(BackgroundMusic.THEME.CUSTOMS);
            } else {
                BackgroundMusic.getInstance().setTheme(BackgroundMusic.THEME.DEFAULT);
            }
        });
        musicContent.add(theme, 0, 2);
        musicContent.add(choiceTheme, 1, 2);

        // GRAPHICS TAB
        Tab graphicsTab = new Tab("Graphics");
        GridPane graphicsContent = new GridPane();
        graphicsContent.setAlignment(Pos.CENTER);
        graphicsTab.setContent(graphicsContent);

        Text zoom = new Text("Zoom");
        Slider sliderZoom = new Slider();
        {
            sliderZoom.setOrientation(Orientation.HORIZONTAL);
            sliderZoom.setValue(GameConfig.ZOOM);
            sliderZoom.setMin(1);
            sliderZoom.setMax(1.5);
            sliderZoom.setBlockIncrement(0.25);
            sliderZoom.setMajorTickUnit(0.25);
            sliderZoom.setMinorTickCount(0);
            sliderZoom.setShowTickLabels(true);
            sliderZoom.setShowTickMarks(true);
            sliderZoom.setSnapToTicks(true);
            sliderZoom.valueProperty().addListener(observable -> {
                GameConfig.ZOOM = Math.round(sliderZoom.getValue() * 4) / 4.0;
                if (GameConfig.ZOOM == 1.5) {
                    GameConfig.ZOOM = 1.4;
                }
                Main.zoom();
            });
        }
        graphicsContent.add(zoom, 0,0);
        graphicsContent.add(sliderZoom, 1, 0);

        // CONTROLS TAB
        Tab controlsTab = new Tab("Controls");

        tabPane.getTabs().addAll(musicTab, graphicsTab);
        tabPane.setTabMinWidth(DEFAULT_WIDTH / 2 - 20);
        getChildren().addAll(title, tabPane);
    }

    public void setMute(boolean mute) {
        isMute.setSelected(mute);
    }
}
