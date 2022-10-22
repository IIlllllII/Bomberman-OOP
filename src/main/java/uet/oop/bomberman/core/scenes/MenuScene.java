package uet.oop.bomberman.core.scenes;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.scenes.buttons.MenuButton;
import uet.oop.bomberman.core.scenes.menu.Leaderboards;
import uet.oop.bomberman.core.scenes.menu.Setting;
import uet.oop.bomberman.core.scenes.menu.SliderShow;

public class MenuScene {
    private final StackPane root;
    private static SliderShow background;
    private final GameMenu gameMenu;

    public static void init() {
        SliderShow.init();
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
            System.out.println("click from menu");
            fadeIn();
        });

//        root.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ESCAPE) {
//                fadeOut();
//            }
//        });
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
    }

    public void fadeIn() {
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
            setSpacing(50);

            ImageView logo = new ImageView(new Image("/Logo1.png"));
            logo.setFitHeight(79.875);
            logo.setFitWidth(450);

            VBox menu0 = new VBox(12);
            VBox menu1 = new VBox(12);

            getChildren().add(0, logo);
            getChildren().add(1, menu0);

            Leaderboards leaderboards = new Leaderboards(350, 160);
            Setting setting = new Setting(350, 180);

            MenuButton buttonPlay = new MenuButton("New Game", Color.GREEN);
            buttonPlay.setOnMouseClicked(mouseEvent -> {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(event ->
                {
                    background.stop();
                    this.setVisible(false);
                    SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.PLAY);
                });
                ft.play();
            });

            MenuButton btnBack = new MenuButton("Back", Color.CRIMSON);
            btnBack.setOnMouseClicked(event -> {
                menu1.getChildren().clear();
                getChildren().remove(menu1);
                getChildren().add(1, menu0);
            });

            MenuButton btnLeaderboards = new MenuButton("Leaderboards", Color.BLUE);
            btnLeaderboards.setOnMouseClicked(event -> {
                getChildren().remove(menu0);
                menu1.getChildren().add(0, leaderboards);
                menu1.getChildren().add(1, btnBack);
                getChildren().add(1, menu1);
            });

            MenuButton btnSetting = new MenuButton("Setting", Color.BLUE);
            btnSetting.setOnMouseClicked(event -> {
                getChildren().remove(menu0);
                menu1.getChildren().add(0, setting);
                menu1.getChildren().add(1, btnBack);
                getChildren().add(1, menu1);
            });

            MenuButton btnAbout = new MenuButton("About", Color.BLUE);
            btnAbout.setOnMouseClicked(event -> {

            });

            MenuButton btnExit = new MenuButton("Exit", Color.RED);
            btnExit.setOnMouseClicked(event -> {
                Platform.exit();
                System.exit(0);
            });

            menu0.getChildren().addAll(buttonPlay, btnLeaderboards, btnSetting, btnAbout, btnExit);
        }
    }
}
