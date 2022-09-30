package uet.oop.bomberman.core;

import javafx.scene.image.Image;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.config.GameScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class ScreenManager {
    private static GameScreen screen = GameScreen.GREETING;
    private static Image background;
    private static int level = 1;
    private static boolean loadAsset = false;

    public static GameScreen getScreen() {
        return screen;
    }

    public static void setScreen(GameScreen screen) {
        ScreenManager.screen = screen;
    }

    public static Image getBackground() {
        return background;
    }

    public static void setBackground(Image background) {
        ScreenManager.background = background;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        ScreenManager.level = level;
    }

    public static void nextScreen() {
        loadAsset = false;
        screen = screen.next();
    }

    public static void prevScreen() {
        loadAsset = false;
        screen = screen.prev();
    }

    public static void nextLevel() {
        loadAsset = false;
        level++;
    }

    public static void prevLevel() {
        loadAsset = false;
        level--;
    }

    public static void renderCurrentScreen() throws FileNotFoundException {
        /**
         * Check if assets of the current screen are loaded or not.
         */
        if (!loadAsset) {
            loadAsset = true;
            loadCurrentScreen();
        } else {
            Renderer.renderScreen(screen);
            Renderer.updateScreen(screen);
        }
    }

    public static void loadCurrentScreen() throws FileNotFoundException {
        System.out.println(screen);
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
                    nextScreen();
                    level = 1;
                } else if (level < 1) {
                    prevScreen();
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

    private static void loadGreeting() throws FileNotFoundException {
        InputStream stream = new FileInputStream(GameConfig.GREETING_PATH);
        background = new Image(stream);
    }

    private static void loadMainMenu() throws FileNotFoundException {
        InputStream stream = new FileInputStream(GameConfig.MAIN_MENU_PATH);
        background = new Image(stream);
    }

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
        EntitiesManager entitiesManager = EntitiesManager.getInstance();

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
                    //entitiesManager.grasses.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    switch (line.charAt(i)) {
                        case '#':
//                            entitiesManager.stillObjects.add(
//                                    new Wall(i, j, Sprite.wall.getFxImage())
//                            );
                            break;
                        case '*':
//                            entitiesManager.stillObjects.add(
//                                    new Brick(i, j, Sprite.brick.getFxImage())
//                            );
                            break;
                        case 'x':
//                            entitiesManager.stillObjects.add(
//                                    new Portal(i, j, Sprite.portal.getFxImage())
//                            );
//                            entitiesManager.stillObjects.add(
//                                    new Brick(i, j, Sprite.brick.getFxImage())
//                            );
                            break;
                        case 'p':
//                            entitiesManager.players.add(
//                                    new Bomber(i, j)
//                            );
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
