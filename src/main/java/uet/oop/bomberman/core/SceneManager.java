package uet.oop.bomberman.core;

import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.*;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.config.GameScene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class SceneManager {
    private static GameScene screen = GameScene.GREETING;
    private static Image background;
    private static int level = 1;

    public static GameScene getScreen() {
        return screen;
    }

    public static void setScreen(GameScene screen) {
        SceneManager.screen = screen;
    }

    public static Image getBackground() {
        return background;
    }

    public static void setBackground(Image background) {
        SceneManager.background = background;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        SceneManager.level = level;
    }

    public static void loadCurrentScene() throws FileNotFoundException {
        switch (screen) {
            case GREETING:
                loadGreeting();
                break;
            case MAIN_MENU:
                loadMainMenu();
                break;
            case SETTINGS:
                loadSettings();
                break;
            case TUTORIAL:
                loadTutorials();
                break;
            case HIGHSCORE:
                loadHighscore();
                break;
            case PLAYING:
                if (level > GameConfig.LEVEL_MAX) {
                    screen = screen.next();
                    level = 1;
                } else {
                    loadLevelMap();
                }
                break;
            case ENDING:
                loadEnding();
                break;
        }


    }

    /**
     * Load greeting screen.
     * TODO:
     */
    private static void loadGreeting() throws FileNotFoundException {
        InputStream stream = new FileInputStream(GameConfig.GREETING_PATH);
        background = new Image(stream);
    }

    /**
     * Load main menu screen.
     * TODO:
     */
    private static void loadMainMenu() throws FileNotFoundException {
        InputStream stream = new FileInputStream(GameConfig.MAIN_MENU_PATH);
        background = new Image(stream);
    }

    /**
     * Load setting screen.
     * TODO:
     */
    private static void loadSettings() throws FileNotFoundException {
        InputStream stream = new FileInputStream(GameConfig.SETTINGS_PATH);
        background = new Image(stream);
    }

    private static void loadTutorials() throws FileNotFoundException {
        InputStream stream = new FileInputStream(GameConfig.TUTORIALS_PATH);
        background = new Image(stream);
    }

    private static void loadHighscore() throws FileNotFoundException {
        InputStream stream = new FileInputStream(GameConfig.HIGH_SCORE_PATH);
        background = new Image(stream);
    }

    private static void loadEnding() throws FileNotFoundException {
        InputStream stream = new FileInputStream(GameConfig.ENDING_PATH);
        background = new Image(stream);
    }

    /**
     * Load data of level map from file .txt
     * TODO: add more entities.
     */
    private static void loadLevelMap() {
        try {
            Scanner sc = new Scanner(
                    new File(GameConfig.LEVEL_DATA[level - 1])
            );
            int L = sc.nextInt();       //Level number.
            int R = sc.nextInt();       //Rows number.
            int C = sc.nextInt();       //Columns number.
            String endLine = sc.nextLine();

            for (int j = 0; j < R; j++) {
                String line = sc.nextLine();
                for (int i = 0; i < C; i++) {
                    GameManager.grasses.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    switch (line.charAt(i)) {
                        case '#':
                            GameManager.stillObjects.add(
                                    new Wall(i, j, Sprite.wall.getFxImage())
                            );
                            break;
                        case '*':
                            GameManager.stillObjects.add(
                                    new Brick(i, j, Sprite.brick.getFxImage())
                            );
                            break;
                        case 'x':
                            GameManager.stillObjects.add(
                                    new Portal(i, j, Sprite.portal.getFxImage())
                            );
                            GameManager.stillObjects.add(
                                    new Brick(i, j, Sprite.brick.getFxImage())
                            );
                            break;
                        case 'p':
                            GameManager.entities.add(
                                    new Bomber(i, j, Sprite.player_right.getFxImage())
                            );
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when loading map");
            e.printStackTrace();
        }
    }


}
