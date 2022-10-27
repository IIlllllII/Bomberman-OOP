package uet.oop.bomberman.core.scenes;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.scenes.buttons.ButtonCustom;
import uet.oop.bomberman.core.scenes.menu.GameMode;
import uet.oop.bomberman.core.scenes.menu.Leaderboards;
import uet.oop.bomberman.core.scenes.menu.Setting;
import uet.oop.bomberman.core.scenes.menu.SliderShow;

import java.net.URISyntaxException;

public class MenuScene {
    private final StackPane root;
    private static SliderShow background;
    private final GameMenu gameMenu;

    public static void init() {
        SliderShow.init();
        Leaderboards.init();
    }

    private static class SingletonHelper {
        private static final MenuScene INSTANCE = new MenuScene();
    }
    public static MenuScene getInstance() {
        return MenuScene.SingletonHelper.INSTANCE;
    }
    private MenuScene() {
        root = new StackPane();
        root.setAlignment(Pos.CENTER);

        background = new SliderShow(2, 4);
        background.setPrefWidth(GameConfig.SCENE_WIDTH);
        background.setPrefHeight(GameConfig.SCENE_HEIGHT);

        gameMenu = new GameMenu();
        gameMenu.setVisible(false);

        root.getChildren().addAll(background, gameMenu);

        root.setOnMouseClicked(event -> {
            fadeIn();
        });
    }

    public void zoom() {
        gameMenu.setScaleX(GameConfig.ZOOM);
        gameMenu.setScaleY(GameConfig.ZOOM);
    }

    public Parent getRoot() {
        return root;
    }

    public void reset() {
        background.reset();
        gameMenu.setVisible(false);
    }

    private void fadeIn() {
        if (!gameMenu.isVisible()) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
            ft.setFromValue(0);
            ft.setToValue(1);
            background.setOpacity(0.8);
            gameMenu.setVisible(true);
            ft.play();
        }
    }

    public void fadeOut() {
        if (gameMenu.isVisible()) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(event -> {
                background.setOpacity(1);
                gameMenu.setVisible(false);
            });
            ft.play();
        }
    }

    private static class GameMenu extends VBox {
        public GameMenu() {
            setStyle("-fx-background-color: rgba(128, 128, 128, 0.5)");
            setAlignment(Pos.CENTER);
            setSpacing(40);

            ImageView logo;
            try {
                logo = new ImageView(new Image(getClass().getResource("/UI/Logo1.png").toURI().toString()));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            logo.setFitHeight(79);
            logo.setFitWidth(450);

            VBox menu0 = new VBox(12);
            menu0.setMaxWidth(280);
            menu0.setPadding(new Insets(30, 0, 0, 0));

            VBox menu1 = new VBox(15);

            getChildren().add(0, logo);
            getChildren().add(1, menu0);

            Leaderboards leaderboards = Leaderboards.getInstance();
            Setting setting = Setting.getInstance();
            GameMode gameMode = new GameMode();

            ButtonCustom btnBack = new ButtonCustom("Back", Color.CRIMSON, 150, 30);
            btnBack.setOnMouseClicked(event -> {
                menu1.getChildren().clear();
                getChildren().remove(menu1);
                getChildren().add(1, menu0);
            });

            ButtonCustom btnBack1 = new ButtonCustom("Back", Color.CRIMSON, 150, 30);
            btnBack1.setOnMouseClicked(event -> {
                menu1.getChildren().clear();
                getChildren().remove(menu1);
                getChildren().add(1, menu0);
            });

            ButtonCustom btnPlay = new ButtonCustom("Play", Color.DARKBLUE, Color.LIGHTGREEN, 180, 30);
            btnPlay.setOnMouseClicked(mouseEvent -> {
                background.stop();
                SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.PLAY);

                menu1.getChildren().clear();
                getChildren().remove(menu1);
                getChildren().add(1, menu0);
            });

            HBox btnMode = new HBox(200);
            btnMode.setAlignment(Pos.CENTER);
            btnMode.getChildren().addAll(btnBack1, btnPlay);

            ButtonCustom buttonNewGame = new ButtonCustom("New Game", Color.GREEN, 220, 45);
            buttonNewGame.setOnMouseClicked(mouseEvent -> {
                getChildren().remove(menu0);
                menu1.getChildren().addAll(gameMode, btnMode);
                getChildren().add(1, menu1);
            });

            ButtonCustom btnLeaderboards = new ButtonCustom("Leaderboards", Color.BLUE, 200, 35);
            btnLeaderboards.setOnMouseClicked(event -> {
                getChildren().remove(menu0);
                menu1.getChildren().add(0, leaderboards);
                menu1.getChildren().add(1, btnBack);
                getChildren().add(1, menu1);
            });

            ButtonCustom btnSetting = new ButtonCustom("Setting", Color.BLUE, 200, 35);
            btnSetting.setOnMouseClicked(event -> {
                getChildren().remove(menu0);
                menu1.getChildren().add(0, setting);
                menu1.getChildren().add(1, btnBack);
                getChildren().add(1, menu1);
            });

            ButtonCustom btnAbout = new ButtonCustom("About", Color.BLUE, 200, 35);
            btnAbout.setOnMouseClicked(event -> {

            });

            ButtonCustom btnExit = new ButtonCustom("Exit", Color.RED, 200, 35);
            btnExit.setOnMouseClicked(event -> {
                Platform.exit();
                System.exit(0);
            });

            menu0.getChildren().addAll(buttonNewGame, btnLeaderboards, btnSetting, btnExit);
        }
    }
}
