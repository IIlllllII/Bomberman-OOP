package uet.oop.bomberman.components.maps;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.entities.enemies.bosses.Banana;
import uet.oop.bomberman.components.entities.enemies.bosses.Saru;
import uet.oop.bomberman.components.entities.enemies.normal.*;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.items.item_types.*;
import uet.oop.bomberman.components.entities.materials.Brick;
import uet.oop.bomberman.components.entities.materials.Grass;
import uet.oop.bomberman.components.entities.materials.Portal;
import uet.oop.bomberman.components.entities.materials.Wall;
import uet.oop.bomberman.components.entities.players.AutoPlay;
import uet.oop.bomberman.components.entities.players.Player;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.scenes.PlayScene;
import uet.oop.bomberman.core.scenes.game.Clock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LevelMap {
    private char[][] mapHash;
    private Grass grass;
    private Wall wall;
    private int level;
    private boolean levelComplete;
    private boolean auto = true;
    private final EntitiesManager entitiesManager = EntitiesManager.getInstance();

    private static class SingletonHelper {
        private static final LevelMap INSTANCE = new LevelMap();
    }

    public static LevelMap getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private LevelMap() {
        reset();
    }

    public void reset() {
        levelComplete = false;
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
        for (int i = 0; i < mapHash.length; ++i) {
            for (int j = 0; j < mapHash[i].length; ++j) {
                grass.setLocation(32 * j, 32 * i);
                grass.render(gc);
                if (mapHash[i][j] == getHash("wall")) {
                    wall.setLocation(32 * j, 32 * i);
                    wall.render(gc);
                }
            }
        }
    }

    public void update() {
        if (levelComplete && PlayScene.getClock().isDone()) {
            nextLevel();
            levelComplete = false;
        }
    }

    public void setLevelComplete(boolean levelComplete) {
        this.levelComplete = levelComplete;
    }

    public void prepareNextLevel() {
        levelComplete = true;
        PlayScene.setClock(15);

        //Change all bricks left into coins:
        entitiesManager.bricks.forEach(brick -> {
            if(!brick.isDestroyed()){
                entitiesManager.coins.add(new Coin(brick.getX(), brick.getY()));
                mapHash[(int)brick.getY() / GameConfig.TILE_SIZE][(int)brick.getX() / GameConfig.TILE_SIZE]
                        = getHash("grass");
            }
        });
        entitiesManager.bricks.clear();
    }

    // Should be private later
    public void nextLevel() {
        level++;
        level = (level > 8) ? 1 : level;
        grass = new Grass(0, 0, level);
        wall = new Wall(0, 0, level);
        entitiesManager.renewEntities();
        List<Brick> brickList = entitiesManager.bricks;
        List<Item> itemList = entitiesManager.items;
        List<Enemy> enemyList = entitiesManager.enemies;
        Portal portal = entitiesManager.portal;

        if (PlayScene.getClock() != null) {
            PlayScene.setClock(Clock.DEFAULT_TIME);
        }

        System.out.println("Current: " + brickList.size() + " " + itemList.size()
        + " " + enemyList.size());

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
                            if(level == 1){
                                if(auto){
                                    entitiesManager.players.add(
                                            new AutoPlay(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE, 16, 22)
                                    );
                                }else {
                                    entitiesManager.players.add(
                                            new Player(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE, 16, 22)
                                    );
                                }
                            }else {
                                entitiesManager.players.get(0).setLocation(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE);
                            }
                            hash = getHash("grass");
                            break;
                        }
                        case '1': {
                            enemyList.add(new Balloom(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE));
                            hash = getHash("grass");
                            break;
                        }
                        case '2': {
                            enemyList.add(new Oneal(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE));
                            hash = getHash("grass");
                            break;
                        }
                        case '3': {
                            enemyList.add(new Dahl(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE));
                            hash = getHash("grass");
                            break;
                        }
                        case '4': {
                            enemyList.add(new Minvo(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE));
                            hash = getHash("grass");
                            break;
                        }
                        case '5': {
                            enemyList.add(new Doria(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE));
                            hash = getHash("grass");
                            break;
                        }
                        case '6': {
                            enemyList.add(new Ovape(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE));
                            hash = getHash("grass");
                            break;
                        }
                        case '7': {
                            enemyList.add(new Pass(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE));
                            hash = getHash("grass");
                            break;
                        }
                        case '8': {
                            enemyList.add(new Pontan(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE));
                            hash = getHash("grass");
                            break;
                        }
                        case '9': {
                            enemyList.add(new Banana(j * GameConfig.TILE_SIZE + 15, i * GameConfig.TILE_SIZE - 5));
                            hash = getHash("grass");
                            break;
                        }
                        case 'S': {
                            enemyList.add(new Saru(j * GameConfig.TILE_SIZE, i * GameConfig.TILE_SIZE));
                            hash = getHash("grass");
                            break;
                        }
                        case '*': {
                            brickList.add(new Brick(GameConfig.TILE_SIZE * j, GameConfig.TILE_SIZE * i, level));
                            break;
                        }
                        default:
                            break;
                    }
                    mapHash[i][j] = hash;
                }
            }
            if (level == 8) return;
            Random r = new Random();
            int index = r.nextInt(brickList.size());
            System.out.println("Portal index: " + index);
            portal.setLocation(brickList.get(index).getX(), brickList.get(index).getY());
            double xItem = 0;
            double yItem = 0;
            String tile = scanner.nextLine();
            for(int i = 0; i < tile.length(); i++){
                char hash = tile.charAt(i);
                boolean checkItem = false;
                while (!checkItem) {
                    int ran = r.nextInt(brickList.size());
                    boolean check = false;
                    if (brickList.get(ran).getX() == portal.getX()
                            && brickList.get(ran).getY() == portal.getY()) {
                        check = true;
                    }
                    for (int j = 0; j < itemList.size(); j++) {
                        if (brickList.get(ran).getX() == itemList.get(j).getX()
                                && brickList.get(ran).getY() == itemList.get(j).getY()) {
                            check = true;
                        }
                    }
                    if(!check){
                        xItem = brickList.get(ran).getX();
                        yItem = brickList.get(ran).getY();
                        checkItem = true;
                    }
                }

                switch (hash) {
                    case 'b': {
                        itemList.add(new BombUp(xItem, yItem));
                        break;
                    }
                    case 'f': {
                        itemList.add(new FlameUp(xItem, yItem));
                        break;
                    }
                    case 's': {
                        itemList.add(new SpeedUp(xItem, yItem));
                        break;
                    }
                    case 'B': {
                        itemList.add(new BombPass(xItem, yItem));
                        break;
                    }
                    case 'F': {
                        itemList.add(new FlamePass(xItem, yItem));
                        break;
                    }
                    case 'W': {
                        itemList.add(new BrickPass(xItem, yItem));
                        break;
                    }
                    case 'I': {
                        itemList.add(new Initialized(xItem, yItem));
                        break;
                    }
                    case 'l': {
                        itemList.add(new LivesUp(xItem, yItem));
                        break;
                    }
                    default:
                        break;
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

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public int getWidth() {
        return this.mapHash[0].length * GameConfig.TILE_SIZE;
    }

    public int getHeight() {
        return this.mapHash.length * GameConfig.TILE_SIZE;
    }

    public void destroyBrick(int i, int j) {
        List<Brick> brickList = EntitiesManager.getInstance().bricks;
        List<Item> itemList = EntitiesManager.getInstance().items;
        Portal portal = EntitiesManager.getInstance().portal;
        brickList.forEach(brick -> {
            if ((j * GameConfig.TILE_SIZE) == brick.getX() && (i * GameConfig.TILE_SIZE) == brick.getY()) {
                brick.setDestroyed(true);
            }
        });

        itemList.forEach(item -> {
            if ((GameConfig.TILE_SIZE * j) == item.getX() && (GameConfig.TILE_SIZE * i) == item.getY()) {
                item.setAppear(true);
            }
        });
        mapHash[i][j] = getHash("grass");
        if ((j * GameConfig.TILE_SIZE) == portal.getX() && (i * GameConfig.TILE_SIZE) == portal.getY()) {
            portal.setAppear(true);
            mapHash[i][j] = getHash("portal");
        }
    }

    public char[][] getMapHash() {
        return this.mapHash;
    }

    public char getHashAt(int i, int j) {
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
                break;
            case "coin":
                output = '$';
                break;
            case "portal":
                output = 'x';
                break;
            default:
                break;
        }
        return output;
    }
}