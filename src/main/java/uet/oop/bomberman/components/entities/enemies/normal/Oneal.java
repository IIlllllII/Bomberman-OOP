package uet.oop.bomberman.components.entities.enemies.normal;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;

import java.util.*;

public class Oneal extends Enemy {

    public Oneal(double x, double y) {
        super(x, y);

        animationLeft = new Animation(SpriteSheet.enemy, 3, 3, 3000, 0, 32, 32, 32);
        animationRight = new Animation(SpriteSheet.enemy, 3, 3, 3000, 96, 32, 32, 32);
        animationDeath = new Animation(SpriteSheet.enemy, 3, 3, 1000, 192, 32, 32, 32);
        animationRight.setLoop(true);
        animationLeft.setLoop(true);
        initDirectionList();

        score = 200;
    }

    @Override
    protected void move() {
        int j = (int) (x / GameConfig.TILE_SIZE);
        int i = (int) (y / GameConfig.TILE_SIZE);

        if (Math.abs((double) j * GameConfig.TILE_SIZE - x) < speed && Math.abs((double) i * GameConfig.TILE_SIZE - y) < speed) {
            moveX = 0;
            moveY = 0;
            int temp = random.nextInt(3);
            speed = (temp == 0) ? 1 : temp;

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

    private Direction findWay(int i, int j) {
        double bomberX = EntitiesManager.getInstance().bombers.get(0).getX();
        double bomberY = EntitiesManager.getInstance().bombers.get(0).getY();

        int jBomber = (int) (bomberX + GameConfig.TILE_SIZE / 2) / GameConfig.TILE_SIZE;
        int iBomber = (int) (bomberY + GameConfig.TILE_SIZE / 2) / GameConfig.TILE_SIZE;

        int ran = random.nextInt(directionList.size());
        if (Math.abs(jBomber - j) > 2 || Math.abs(iBomber - i) > 2){
            return directionList.get(ran);
        }

        LevelMap levelMap = LevelMap.getInstance();
        boolean[][] checkPass =
                new boolean[levelMap.getMapHash().length][levelMap.getMapHash()[0].length];

        for (int m = 0; m < checkPass.length; m++) {
            for (int n = 0; n < checkPass[0].length; n++) {
                checkPass[m][n] = false;
            }
        }

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
                return direction;
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
        return directionList.get(ran);
    }
}


