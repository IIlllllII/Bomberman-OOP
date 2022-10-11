package uet.oop.bomberman.components.maps;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.enemy.Balloom;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.entities.stillobjects.Brick;
import uet.oop.bomberman.components.entities.stillobjects.Grass;
import uet.oop.bomberman.components.entities.stillobjects.Wall;
import uet.oop.bomberman.config.GameConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LevelMap {
    private char[][] mapHash;
    private Grass grass;
    private Wall wall;
    private Brick brick;
    private int level;
    private final EntitiesManager entitiesManager = EntitiesManager.getInstance();

    private static class SingletonHelper {
        private static final LevelMap INSTANCE = new LevelMap();
    }

    public static LevelMap getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private LevelMap() {
        level = 0;
        nextLevel();
    }

    public static void init() {
        Grass.init();
        Wall.init();
        Brick.init();
    }

    public void render(GraphicsContext gc) {
        for(int i = 0; i < mapHash.length; ++i) {
            for(int j = 0; j < mapHash[i].length; ++j) {
                grass.setLocation(32 * j, 32 * i);
                grass.render(gc);

                if (mapHash[i][j] == getHash("wall")) {
                    wall.setLocation(32 * j, 32 * i);
                    wall.render(gc);
                }

                if (mapHash[i][j] == getHash("brick")) {
                    brick.setLocation(32 * j, 32 * i);
                    brick.render(gc);
                }
            }
        }
    }

    public void update(){
    }
    public void nextLevel() {
        level++;
        level = (level > 3) ? 1 : level;

        grass = new Grass(0,0, 0 , 0, level);
        wall = new Wall(0,0, 0, 0, level);
        brick = new Brick(0, 0, 0, 0, level);

        EntitiesManager.getInstance().renewEntities();

        try {
            File file = new File(GameConfig.LEVEL_DATA[level - 1]);
            Scanner scanner = new Scanner(file);
            int row = scanner.nextInt();
            int column = scanner.nextInt();
            String temp = scanner.nextLine();   //skip end-line after reading integers.

            mapHash = new char[row][column];
            for (int i = 0; i < row; i++) {
                String tile = scanner.nextLine();
                for (int j = 0; j < column; j++) {
                    char hash = tile.charAt(j);

                    switch (hash) {
                        case 'p': {
                            entitiesManager.players.add(
                                    new Bomber(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE,
                                            16 * GameConfig.SCALED_FACTOR, 22 * GameConfig.SCALED_FACTOR)
                            );
                            hash = getHash("grass");
                            break;
                        }
                        case '1': {
                            entitiesManager.enemy.add(new Balloom(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE));
                            hash = getHash("grass");
                            break;
                        }
                    }

                    mapHash[i][j] = hash;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("next level read file");
            throw new RuntimeException(e);
        }
    }

//    public void destroyBrick(int i, int j) {
//        Point2D point = new Point2D(j, i);
//        if (brickList.containsKey(point)) {
//            brickList.get(point).setDestroyed(true);
//        }
//    }

    public void destroyBrick(int i, int j) {
        mapHash[i][j] = getHash("grass");
        Brick brokenBrick = new Brick(j * 32, i * 32, 32, 32, level);
        brokenBrick.setDestroyed(true);
        EntitiesManager.getInstance().brokenBricks.add(brokenBrick);
    }

    public int getLevel() {
        return level;
    }

    public int getWidth() {
        return this.mapHash[0].length * 32;
    }

    public int getHeight() {
        return this.mapHash.length * 32;
    }

    public char[][] getMapHash() {
        return this.mapHash;
    }

    public char getHashAt(int i, int j){
        return mapHash[i][j];
    }

    public char getHash(String input) {
        char output = ' ';
        switch (input) {
            case "grass":
                output = ' ';
                break;
            case "brick":
                output = '*';
                break;
            case "wall":
                output = '#';
                break;
            default:
                break;
        }
        return output;
    }
}
