package uet.oop.bomberman.components.entities.enemies.normal;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;

import java.util.LinkedList;
import java.util.Queue;

public class Doria extends Enemy {

    public Doria(double x, double y) {
        super(x, y);
        animationLeft = new Animation(SpriteSheet.enemy, 3, 3, 1000, 0, 160, 32, 32);
        animationRight = new Animation(SpriteSheet.enemy, 3, 3, 1000, 96, 160, 32, 32);
        animationDeath = new Animation(SpriteSheet.enemy, 4, 4, 1000, 192, 160, 32, 32);
        animationLeft.setLoop(true);
        animationRight.setLoop(true);
        initDirectionList();
        speed = 0.5;
        score = 1000;
    }

    @Override
    protected void move() {
        int j = (int) (x / GameConfig.TILE_SIZE);
        int i = (int) (y / GameConfig.TILE_SIZE);
        if (Math.abs((double) j * GameConfig.TILE_SIZE - x) < speed && Math.abs((double) i * GameConfig.TILE_SIZE - y) < speed) {
            moveX = 0;
            moveY = 0;
            currentDirection = findWay(i, j);

            canMoveR = checkMapHash(i, j + 1);
            canMoveL = checkMapHash(i, j - 1);
            canMoveU = checkMapHash(i - 1, j);
            canMoveD = checkMapHash(i + 1, j);
            checkMove();
        }
        x += moveX;
        y += moveY;
    }

    @Override
    protected boolean checkMapHash(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();
        if (i < 0 || i > (levelMap.getHeight() / GameConfig.TILE_SIZE) - 1
                || j < 0 || j > (levelMap.getWidth() / GameConfig.TILE_SIZE) - 1) {
            return false;
        }
        return levelMap.getHashAt(i, j) == levelMap.getHash("grass")
                || levelMap.getHashAt(i, j) == levelMap.getHash("brick");
    }

    private Direction findWay(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();
        boolean[][] checkPass =
                new boolean[levelMap.getMapHash().length][levelMap.getMapHash()[0].length];

        for (int m = 0; m < checkPass.length; m++) {
            for (int n = 0; n < checkPass[0].length; n++) {
                checkPass[m][n] = false;
            }
        }

        double bomberX = EntitiesManager.getInstance().bombers.get(0).getX();
        double bomberY = EntitiesManager.getInstance().bombers.get(0).getY();

        int jBomber = (int) (bomberX + GameConfig.TILE_SIZE / 2) / GameConfig.TILE_SIZE;
        int iBomber = (int) (bomberY + GameConfig.TILE_SIZE / 2) / GameConfig.TILE_SIZE;

        canMoveR = checkMapHash(i, j + 1);
        canMoveL = checkMapHash(i, j - 1);
        canMoveU = checkMapHash(i - 1, j);
        canMoveD = checkMapHash(i + 1, j);

        Queue<Direction> direc = new LinkedList<>();
        Queue<Integer> iTile = new LinkedList<>();
        Queue<Integer> jTile = new LinkedList<>();

        checkPass[i][j] = true;
        if (canMoveR && !checkPass[i][j + 1]) {
            checkPass[i][j + 1] = true;
            direc.add(Direction.RIGHT);
            iTile.add(i);
            jTile.add(j + 1);
        }
        if (canMoveL && !checkPass[i][j - 1]) {
            checkPass[i][j - 1] = true;
            direc.add(Direction.LEFT);
            iTile.add(i);
            jTile.add(j - 1);
        }
        if (canMoveU && !checkPass[i - 1][j]) {
            checkPass[i - 1][j] = true;
            direc.add(Direction.UP);
            iTile.add(i - 1);
            jTile.add(j);
        }
        if (canMoveD && !checkPass[i + 1][j]) {
            checkPass[i + 1][j] = true;
            direc.add(Direction.DOWN);
            iTile.add(i + 1);
            jTile.add(j);
        }

        while (!direc.isEmpty()) {
            Direction direction = direc.poll();
            i = iTile.poll();
            j = jTile.poll();
            if (i == iBomber && j == jBomber) {
                for(int k = 0; k < directionList.size(); k++){
                    if(directionList.get(k) == direction){
                        return direction;
                    }
                }
            }

            canMoveR = checkMapHash(i, j + 1);
            canMoveL = checkMapHash(i, j - 1);
            canMoveU = checkMapHash(i - 1, j);
            canMoveD = checkMapHash(i + 1, j);

            if (canMoveR && !checkPass[i][j + 1]) {
                checkPass[i][j + 1] = true;
                direc.add(direction);
                iTile.add(i);
                jTile.add(j + 1);
            }
            if (canMoveL && !checkPass[i][j - 1]) {
                checkPass[i][j - 1] = true;
                direc.add(direction);
                iTile.add(i);
                jTile.add(j - 1);
            }
            if (canMoveU && !checkPass[i - 1][j]) {
                checkPass[i - 1][j] = true;
                direc.add(direction);
                iTile.add(i - 1);
                jTile.add(j);
            }
            if (canMoveD && !checkPass[i + 1][j]) {
                checkPass[i + 1][j] = true;
                direc.add(direction);
                iTile.add(i + 1);
                jTile.add(j);
            }
        }

        int ran = r.nextInt(directionList.size());
        return directionList.get(ran);
    }
}
