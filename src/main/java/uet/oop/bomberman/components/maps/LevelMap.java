package uet.oop.bomberman.components.maps;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.powerUp.*;
import uet.oop.bomberman.components.entities.stillobjects.Brick;
import uet.oop.bomberman.components.entities.stillobjects.Grass;
import uet.oop.bomberman.components.entities.stillobjects.Portal;
import uet.oop.bomberman.components.entities.stillobjects.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;

public class LevelMap {
    private char[][] mapHash;
    private Grass grass;
    private Wall wall;
    private List<Brick> brickList = new ArrayList<>();

    private List<PowerUp> powerUpList = new ArrayList<>();

    private Portal portal;

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
        Portal.init();
        PowerUp.init();
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
        //Render all power up
        powerUpList.forEach(powerUp -> powerUp.render(gc));
        //Render all destroyed bricks
        brickList.forEach(entity -> entity.render(gc));
    }

    public void update(){

        brickList.forEach(Brick::update);
        powerUpList.forEach(PowerUp::update);
    }
    public void nextLevel() {
        level++;
        level = (level > 3) ? 1 : level;

        grass = new Grass(0,0, 0 , 0, level);
        wall = new Wall(0,0, 0, 0, level);
        brickList.clear();
        powerUpList.clear();

        try {
            File file = new File(LevelMap.class.getResource("/levels/level" + level + ".txt").toURI());
            Scanner scanner = new Scanner(file);
            int row = scanner.nextInt();
            int column = scanner.nextInt();
            String temp = scanner.nextLine();   //skip endline after reading integers.

            mapHash = new char[row][column];
            for (int i = 0; i < row; i++) {
                String tile = scanner.nextLine();
                for (int j = 0; j < column; j++) {
                    char hash = tile.charAt(j);
                    mapHash[i][j] = hash;
                    switch (mapHash[i][j]) {
                        case '*':
                            brickList.add(new Brick(32 * j, 32 * i, 32, 32, level));
                            break;
                        case 'b':
                            powerUpList.add(new PlusBombItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, 32, 32, level));
                            mapHash[i][j] = '*';
                        case 'f':
                            powerUpList.add(new PlusFlameItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, 32, 32, level));
                            mapHash[i][j] = '*';
                            break;
                        case 's':
                            powerUpList.add(new PlusSpeedItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, 32, 32, level));
                            mapHash[i][j] = '*';
                            break;
                        case 'B':
                            powerUpList.add(new BombPassItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, 32, 32, level));
                            mapHash[i][j] = '*';
                            break;
                        case 'F':
                            powerUpList.add(new FlamePassItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, 32, 32, level));
                            mapHash[i][j] = '*';
                            break;
                        case 'W':
                            powerUpList.add(new BrickPassItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, 32, 32, level));
                            mapHash[i][j] = '*';
                            break;
                        case 'l':
                            powerUpList.add(new PlusLiveItem(32 * j, 32 * i));
                            brickList.add(new Brick(32 * j, 32 * i, 32, 32, level));
                            mapHash[i][j] = '*';
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (URISyntaxException | FileNotFoundException e) {
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

    public void destroyBrick(int i, int j){
        mapHash[i][j] = getHash("grass");
        for(int k=0; k< brickList.size(); k++){
            if((j * 32) == brickList.get(k).getX() && (i * 32) == brickList.get(k).getY()){
                brickList.get(k).setDestroyed(true);
            }
        }
    }

    public char[][] getMapHash() {
        return this.mapHash;
    }

    public int getHashAt(int i, int j){
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
    public String checkBrick(int i, int j){
        char hash = mapHash[i][j];
        if( hash == 'b' || hash == 'f' || hash == 's' || hash == 'B' || hash == 'F' || hash == 'W' || hash == 'l'){
            return "powerUp";
        }
        if(hash == '*'){
            return "brick";
        }
        return "other";
    }

    public void appearPowerUp(double x, double y){
        powerUpList.forEach(powerUp -> {
            if(x == powerUp.getX() && y == powerUp.getY()){
                powerUp.setAppear(true);
            }
        });
    }
}
