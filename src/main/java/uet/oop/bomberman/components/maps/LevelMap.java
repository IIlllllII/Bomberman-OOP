package uet.oop.bomberman.components.maps;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.core.Camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Scanner;

public class LevelMap {
    private static LinkedList<Image> map = null;
    private int[][] mapHash;
    private int level;

    public static void init() {
        map = new LinkedList<>();

        try {
            for (int i = 1; i <= 3; i++) {
                map.add(new Image(LevelMap.class.getResource("/map/grass" + i + ".png").toURI().toString()));
                map.add(new Image(LevelMap.class.getResource("/map/wall" + i + ".png").toURI().toString()));
                map.add(new Image(LevelMap.class.getResource("/map/brick" +  i + ".png").toURI().toString()));
            }
        } catch (URISyntaxException | NullPointerException e) {
            System.out.println("Error in map");
        }
    }

    public LevelMap() {
        level = 0;
        mapHash = new int[13][31];

        nextLevel();
    }

    public void render(GraphicsContext render) {
        Camera camera = Camera.getInstance();
        for(int i = 0; i < mapHash.length; ++i) {
            for(int j = 0; j < mapHash[i].length; ++j) {
                int temp = ((level - 1) / 3 ) * 3;
                temp = 6;
                render.drawImage(map.get(temp), (32 * j) - camera.getX(), (32 * i) - camera.getY(), 32, 32);

                if (mapHash[i][j] == 3) {
                    render.drawImage(map.get(2 + temp), (32 * j) - camera.getX(), (32 * i) - camera.getY(), 32, 32);
                } else if (mapHash[i][j] == 1) {
                    render.drawImage(map.get(1 + temp), (32 * j) - camera.getX(), (32 * i) - camera.getY(), 32,32);
                }
            }
        }

    }

    public void nextLevel() {
        level++;
        if (level > 9) {
            level = 1;
        }

        try {
            File file = new File(LevelMap.class.getResource("/map/map" + level + ".map").toURI());
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < 13; i++) {
                String[] tile = (scanner.nextLine()).split(",");
                for (int j = 0; j < 31; j++) {
                    int hash = Integer.parseInt(tile[j]);
                    if (!(hash > 0 && hash < 4)) {
                        if (hash == 9) {
                            hash = 3;
                        } else {
                            hash = 2;
                        }
                    }
                    mapHash[i][j] = hash;
                }
            }
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

//    public int[][] getMapHash() {
//        return this.mapHash;
//    }

    public int getWidth() {
        return this.mapHash[0].length * 32;
    }

    public int getHeight() {
        return this.mapHash.length * 32;
    }
}
