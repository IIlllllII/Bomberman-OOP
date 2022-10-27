package uet.oop.bomberman.core.scenes.game;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.scenes.PlayScene;
import uet.oop.bomberman.core.scenes.SceneManager;
import uet.oop.bomberman.core.scenes.buttons.ButtonCustom;
import uet.oop.bomberman.core.scenes.menu.Leaderboards;
import uet.oop.bomberman.core.scenes.menu.Setting;

public class PauseMenu extends VBox {
    private static class SingletonHelper {
        private static final PauseMenu INSTANCE = new PauseMenu();
    }
    public static PauseMenu getInstance() {
        return PauseMenu.SingletonHelper.INSTANCE;
    }

    private PauseMenu() {
        setStyle("-fx-background-color: rgba(128, 128, 128, 0.2)");
        setAlignment(Pos.CENTER);
        setSpacing(40);
        reset();

        VBox menu0 = new VBox(12);
        menu0.setMaxWidth(280);
        VBox menu1 = new VBox(15);

        getChildren().add(menu0);

        Leaderboards leaderboards = Leaderboards.getInstance();
        Setting setting = Setting.getInstance();

        ButtonCustom btnBack = new ButtonCustom("Back", Color.CRIMSON, 150, 30);
        btnBack.setOnMouseClicked(event -> {
            menu1.getChildren().clear();
            getChildren().remove(menu1);
            getChildren().add( menu0);
        });

        ButtonCustom btnResume = new ButtonCustom("Resume", Color.DARKBLUE, Color.LIGHTGREEN, 250, 40);
        btnResume.setOnMouseClicked(mouseEvent -> {
            PlayScene.getInstance().setStatus(PlayScene.STATUS.PLAYING);
            reset();
        });

        ButtonCustom btnLeaderboards = new ButtonCustom("Leaderboards", Color.BLUE, 200, 35);
        btnLeaderboards.setOnMouseClicked(event -> {
            getChildren().remove(menu0);
            menu1.getChildren().add(0, leaderboards);
            menu1.getChildren().add(1, btnBack);
            getChildren().add( menu1);
        });

        ButtonCustom btnSetting = new ButtonCustom("Setting", Color.BLUE, 200, 35);
        btnSetting.setOnMouseClicked(event -> {
            getChildren().remove(menu0);
            menu1.getChildren().add(0, setting);
            menu1.getChildren().add(1, btnBack);
            getChildren().add( menu1);
        });

        ButtonCustom btnAbout = new ButtonCustom("About", Color.BLUE, 200, 35);
        btnAbout.setOnMouseClicked(event -> {

        });

        ButtonCustom btnExit = new ButtonCustom("Main Menu", Color.RED, 200, 35);
        btnExit.setOnMouseClicked(event -> {
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
            reset();
        });

        menu0.getChildren().addAll(btnResume, btnLeaderboards, btnSetting, btnAbout, btnExit);
    }

    private void reset() {
        setVisible(false);
        setDisable(true);
    }

    public void setEnable() {
        setVisible(true);
        setDisable(false);
    }

    public void zoom() {
        setScaleX(GameConfig.ZOOM);
        setScaleY(GameConfig.ZOOM);
    }
}
