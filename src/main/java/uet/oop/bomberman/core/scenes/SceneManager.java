package uet.oop.bomberman.core.scenes;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class SceneManager {
    public enum SCENES {
        MENU, PLAY
    }

    private static SceneManager instance = null;
    private SCENES currentScene;
    private final MenuScene menuScene;
    private final PlayScene playScene;
    private final Scene scene;

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    private SceneManager() {
        menuScene = new MenuScene();
        playScene = new PlayScene();

        scene = new Scene(menuScene.getRoot());

        currentScene = SCENES.MENU;
    }

    public SCENES getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(SCENES primaryScene) {
        if (primaryScene == SCENES.MENU) {
            currentScene = SCENES.MENU;
            scene.setRoot(menuScene.getRoot());
        }
        if (primaryScene == SCENES.PLAY) {
            currentScene = SCENES.PLAY;
            scene.setRoot(playScene.getRoot());
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void update() {
        switch (currentScene) {
            case MENU: {
                scene.setOnMouseClicked(mouseEvent -> {
                    menuScene.fadeIn();
                });
                scene.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        menuScene.fadeOut();
                    }
                });
                break;
            }
            case PLAY: {
                playScene.update();
                break;
            }
        }
    }

    public void render() {
        switch (currentScene) {
            case MENU: {
                break;
            }
            case PLAY: {
                playScene.render();
                break;
            }
        }
    }
}
