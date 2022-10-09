package uet.oop.bomberman.core.scenes;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.buttons.MenuButton;
import uet.oop.bomberman.sound.Music;
import uet.oop.bomberman.sound.Sound;

public class MenuScene {
    private Group root;
    private Image background;
    private final GameMenu gameMenu;
    public static Music menuMusic = new Music(Music.MENU_MUSIC, true);
    public static Music gameMusic = new Music(Music.GAME_MUSIC, true);

    public MenuScene() {
        root = new Group();

        try {
            background = new Image(getClass().getResource("/bkg.jpg").toURI().toString());
        } catch (Exception e) {
            System.out.println("bkg menu screen");
        }
        ImageView imageView = new ImageView(background);
        imageView.setFitHeight(GameConfig.HEIGHT);
        imageView.setFitWidth(GameConfig.WIDTH);
        gameMusic.setVolume(0.2);
        menuMusic.setVolume(0.2);
        gameMenu = new GameMenu();
        gameMenu.setVisible(false);
        menuMusic.playMusic();

        root.getChildren().addAll(imageView, gameMenu);
    }

    private class GameMenu extends Parent {
        public GameMenu() {
            VBox menu0 = new VBox(10);
            VBox menu1 = new VBox(10);

            menu0.setTranslateX(100);
            menu0.setTranslateY(200);

            menu1.setTranslateX(100);
            menu1.setTranslateY(200);

            final int offset = 400;

            menu1.setTranslateX(offset);

            MenuButton buttonResume = new MenuButton("PLAY");
            buttonResume.setOnMouseClicked(mouseEvent -> {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(event -> this.setVisible(false));
                ft.play();

                SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.PLAY);
                menuMusic.stopMusic();
                gameMusic.playMusic();
                System.out.println("Play button");
            });

            MenuButton btnOptions = new MenuButton("OPTION");
            btnOptions.setOnMouseClicked(event -> {
                getChildren().add(menu1);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu0.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(event1 -> {
                    getChildren().remove(menu0);
                });
            });

            MenuButton btnExit = new MenuButton("EXIT");
            btnExit.setOnMouseClicked(event -> {
                Platform.exit();
                System.exit(0);
            });

            MenuButton btnBack = new MenuButton("BACK");
            btnBack.setOnMouseClicked(event -> {
                getChildren().add(menu0);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu0.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(event1 -> {
                    getChildren().remove(menu1);
                });
            });

            MenuButton btnSound = new MenuButton("SOUND");
            MenuButton btnVideo = new MenuButton("VIDEO");

            menu0.getChildren().addAll(buttonResume, btnOptions, btnExit);
            menu1.getChildren().addAll(btnBack, btnSound, btnVideo);

            Rectangle bg = new Rectangle(GameConfig.WIDTH, GameConfig.HEIGHT);
            bg.setFill(Color.GREY);
            bg.setOpacity(0.4);

            getChildren().addAll(bg, menu0);
        }
    }

    public Group getRoot() {
        return root;
    }

    public void fadeIn() {
        if (!gameMenu.isVisible()) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
            ft.setFromValue(0);
            ft.setToValue(1);

            gameMenu.setVisible(true);
            ft.play();
        }
    }

    public void fadeOut() {
        if (gameMenu.isVisible()) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(event -> gameMenu.setVisible(false));
            ft.play();
        }
    }
}
