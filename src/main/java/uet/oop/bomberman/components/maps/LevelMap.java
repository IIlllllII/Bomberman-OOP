package uet.oop.bomberman.components.maps;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.stillobjects.Brick;
import uet.oop.bomberman.components.entities.stillobjects.Grass;
import uet.oop.bomberman.components.entities.stillobjects.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LevelMap {
    private int[][] mapHash;
    private Grass grass;
    private Wall wall;
    private final static List<Brick> brickList = new ArrayList<>();
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
        brickList.forEach(entity -> entity.render(gc));
    }

    public void nextLevel() {
        level++;
        level = (level > 3) ? 1 : level;

        grass = new Grass(0,0, 0 , 0, level);
        wall = new Wall(0,0, 0, 0, level);
        brickList.clear();

        try {
            File file = new File(LevelMap.class.getResource("/map/map" + level + ".map").toURI());
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
                        brickList.add(
                                new Brick(32 * j, 32 * i, 32, 32, level)
                        );
                    }
                }
            }
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new RuntimeException(e);
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

    public void setMapHash(int i, int j, int value) {
        mapHash[i][j] = value;
    }

    public int getHash(String input) {
        int output = 0;
        switch (input) {
            case "grass":
                //output = ?;
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
