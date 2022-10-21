package uet.oop.bomberman.core.scenes;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.core.sound.Music;

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
    private Music music;

    private final List<KeyCode> inputList = new ArrayList<>();

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    private SceneManager() {
        menuScene = MenuScene.getInstance();
        playScene = new PlayScene();

        scene = new Scene(menuScene.getRoot());
        setCurrentScene(SCENES.MENU);

        music = new Music("src/main/resources/sound/Customs/Outside-calvin-harris.mp3", true);
        music.playMusic();
    }

    public SCENES getCurrentScene() {
        return currentScene;
    }

    void setCurrentScene(SCENES primaryScene) {
        if (primaryScene == SCENES.MENU) {
            currentScene = SCENES.MENU;
            scene.setRoot(menuScene.getRoot());
            menuScene.reset();

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

    public void zoom() {
        menuScene.zoom();
        playScene.zoom();
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
