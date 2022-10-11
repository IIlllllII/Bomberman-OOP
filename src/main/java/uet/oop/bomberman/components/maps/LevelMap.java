package uet.oop.bomberman.components.maps;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.items.*;
import uet.oop.bomberman.components.entities.EntitiesManager;

import uet.oop.bomberman.components.entities.enemy.Balloom;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.entities.stillobjects.Brick;
import uet.oop.bomberman.components.entities.stillobjects.Grass;
import uet.oop.bomberman.components.entities.stillobjects.Portal;
import uet.oop.bomberman.components.entities.stillobjects.Wall;
import uet.oop.bomberman.config.GameConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class LevelMap {
    private char[][] mapHash;
    private Grass grass;
    private Wall wall;

    private Portal portal;

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
        Portal.init();
        Item.init();
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
            }
        }
    }

    public void update(){
    }
    public void nextLevel() {
        level++;
        level = (level > 3) ? 1 : level;

        grass = new Grass(0,0, level);
        wall = new Wall(0,0, level);
        List<Brick> brickList = entitiesManager.bricks;
        List<Item> itemList = entitiesManager.items;
        entitiesManager.renewEntities();

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
                                    new Bomber(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE, 16, 22)
                            );
                            hash = getHash("grass");
                            break;
                        }
                        case '1': {
                            entitiesManager.enemies.add(
                                    new Balloom(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE)
                            );
                            hash = getHash("grass");
                            break;
                        }
                        case '*': {
                            brickList.add(new Brick(32 * j, 32 * i, level));
                            break;
                        }
                        case 'b': {
                            itemList.add(new PlusBombItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, level));
                            hash = getHash("brick");
                            break;
                        }
                        case 'f': {
                            itemList.add(new PlusFlameItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, level));
                            hash = getHash("brick");
                            break;
                        }
                        case 's': {
                            itemList.add(new PlusSpeedItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, level));
                            hash = getHash("brick");
                            break;
                        }
                        case 'B': {
                            itemList.add(new BombPassItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, level));
                            hash = getHash("brick");
                            break;
                        }
                        case 'F': {
                            itemList.add(new FlamePassItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, level));
                            hash = getHash("brick");
                            break;
                        }
                        case 'W': {
                            itemList.add(new BrickPassItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, level));
                            hash = getHash("brick");
                            break;
                        }
                        case 'l': {
                            itemList.add(new PlusLiveItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, level));
                            hash = getHash("brick");
                            break;
                        }
                        default:
                            break;
                    }
                    mapHash[i][j] = hash;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("next level read file");
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

    public void destroyBrick(int i, int j) {
        List<Brick> brickList = EntitiesManager.getInstance().bricks;
        List<Item> itemList = EntitiesManager.getInstance().items;
        brickList.forEach(brick -> {
            if ((j * 32) == brick.getX() && (i * 32) == brick.getY()) {
                brick.setDestroyed(true);
            }
        });

        itemList.forEach(item -> {
            if (32 * j == item.getX() && 32 * i == item.getY()) {
                item.setAppear(true);
            }
        });
        mapHash[i][j] = getHash("grass");
    }

    public char[][] getMapHash() {
        return this.mapHash;
    }

    public char getHashAt(int i, int j){
        return mapHash[i][j];
    }

    public void setHashAt(int i, int j, String input) {
        mapHash[i][j] = getHash(input);
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
            case "bomb":
                output = 'B';
            default:
                break;
        }
        return output;
    }
}
