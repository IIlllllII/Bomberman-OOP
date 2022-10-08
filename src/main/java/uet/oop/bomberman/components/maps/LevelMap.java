package uet.oop.bomberman.components.maps;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.stillobjects.Brick;
import uet.oop.bomberman.components.entities.stillobjects.Grass;
import uet.oop.bomberman.components.entities.stillobjects.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LevelMap {
    private int[][] mapHash;
    private Grass grass;
    private Wall wall;
    private final Map<Point2D, Brick> brickList = new HashMap<>();
    private int level;

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

                if (mapHash[i][j] == 1) {
                    wall.setLocation(32 * j, 32 * i);
                    wall.render(gc);
                }
            }
        }
        //Render all bricks
        brickList.forEach((key, value) -> value.render(gc));
    }

    public void update() {
        brickList.forEach((key, value) -> value.update());
    }

    public void nextLevel() {
        level++;
        level = (level > 3) ? 1 : level;

        grass = new Grass(0,0, 0 , 0, level);
        wall = new Wall(0,0, 0, 0, level);
        brickList.clear();

        try {
            File file = new File(LevelMap.class.getResource("/sprites/map/map" + level + ".map").toURI());
            Scanner scanner = new Scanner(file);
            int row = scanner.nextInt();
            int column = scanner.nextInt();
            String temp = scanner.nextLine();   //skip endline after reading integers.

            mapHash = new int[row][column];
            for (int i = 0; i < row; i++) {
                String[] tile = (scanner.nextLine()).split(",");
                for (int j = 0; j < column; j++) {
                    int hash = Integer.parseInt(tile[j]);

                    mapHash[i][j] = hash;
                    if (hash == 3) {
                        brickList.put(new Point2D(j, i), new Brick(32 * j, 32 * i, 32, 32, level));
                    }
                }
            }
        } catch (URISyntaxException | FileNotFoundException e) {
            System.out.println("next level read file");
            throw new RuntimeException(e);
        }
    }

    public void destroyBrick(int i, int j) {
        Point2D point = new Point2D(j, i);
        if (brickList.containsKey(point)) {
            brickList.get(point).setDestroyed(true);
        }
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

    public int[][] getMapHash() {
        return this.mapHash;
    }

    public int getHash(String input) {
        int output = 0;
        switch (input) {
            case "grass":
                output = 2;
                break;
            case "brick":
                output = 3;
                break;
            case "wall":
                output = 1;
                break;
            default:
                break;
        }
        return output;
    }
}
