package uet.oop.bomberman.core.scenes;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {
    public enum SCENES {
        MENU, PLAY
    }

    private static SceneManager instance = null;
    private SCENES currentScene;
    private final MenuScene menuScene;
    private final PlayScene playScene;
    private final Scene scene;

    private final List<KeyCode> inputList = new ArrayList<>();

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

        setCurrentScene(SCENES.MENU);
    }

    public SCENES getCurrentScene() {
        return currentScene;
    }

    void setCurrentScene(SCENES primaryScene) {
        if (primaryScene == SCENES.MENU) {
            currentScene = SCENES.MENU;

            scene.setRoot(menuScene.getRoot());

            scene.setOnMouseClicked(mouseEvent -> {
                menuScene.fadeIn();
            });
            scene.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    menuScene.fadeOut();
                }
            });
        }
        if (primaryScene == SCENES.PLAY) {
            currentScene = SCENES.PLAY;
            scene.setRoot(playScene.getRoot());

            scene.setOnKeyPressed(event -> {
                KeyCode code = event.getCode();
                if (!inputList.contains(code)) {
                    inputList.add(code);
                }
            });
            scene.setOnKeyReleased(event -> {
                KeyCode code = event.getCode();
                inputList.remove(code);
            });
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void update() {
        switch (currentScene) {
            case MENU: {
                break;
            }
            case PLAY: {
                playScene.update(inputList);
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
