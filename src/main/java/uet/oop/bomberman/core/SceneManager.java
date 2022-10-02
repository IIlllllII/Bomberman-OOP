package uet.oop.bomberman.core;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.scenes.MenuScene;
import uet.oop.bomberman.scenes.PlayScene;

public class SceneManager {
    public enum SCENES {
        MENU, PLAY
    }

    private static SceneManager instance = null;
    private SCENES currentScenes;
    private MenuScene menu;
    private PlayScene play;
    private Scene scene;

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    private SceneManager() {
        menu = new MenuScene();
        play = new PlayScene();

        scene = new Scene(menu.getRoot());

        currentScenes = SCENES.MENU;

        InputHandler inputHandler = InputHandler.getInstance();
        inputHandler.handleKeyPressedEvent(scene);
    }

    public SCENES getCurrentScenes() {
        return currentScenes;
    }

    public void setCurrentScenes(SCENES primaryScenes) {
        if (primaryScenes == SCENES.MENU) {
            currentScenes = SCENES.MENU;
            scene.setRoot(menu.getRoot());
        }
        if (primaryScenes == SCENES.PLAY) {
            currentScenes = SCENES.PLAY;
            scene.setRoot(play.getRoot());
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void update() {
        switch (currentScenes) {
            case MENU: {
                scene.setOnMouseClicked(mouseEvent -> {
                    menu.fadeIn();
                });
                scene.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        menu.fadeOut();
                    }
                });
                break;
            }
            case PLAY: {
                play.update();
                break;
            }
        }
    }

    public void render() {
        switch (currentScenes) {
            case MENU: {
                break;
            }
            case PLAY: {
                play.render();
                break;
            }
        }
    }
}
