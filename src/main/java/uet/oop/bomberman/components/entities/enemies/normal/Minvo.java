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

public class Minvo extends Enemy {
    public Minvo(double x, double y){
        super(x, y);
        animationLeft = new Animation(SpriteSheet.enemy, 3, 3, 1000, 0, 128, 32, 32);
        animationRight = new Animation(SpriteSheet.enemy, 3, 3, 1000, 96, 128, 32, 32);
        animationDeath = new Animation(SpriteSheet.enemy, 4, 4, 1000, 192, 128, 32, 32);
        animationLeft.setLoop(true);
        animationRight.setLoop(true);
        initDirectionList();
        speed = 2;
        score = 800;
    }

    @Override
    protected void move() {
        int j = (int) (x / GameConfig.TILE_SIZE);
        int i = (int) (y  / GameConfig.TILE_SIZE);

        if (j * GameConfig.TILE_SIZE == x && i * GameConfig.TILE_SIZE == y) {
            moveX = 0;
            moveY = 0;
            lastDirection = findWay(i, j);

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
        double bomberX = EntitiesManager.getInstance().players.get(0).getX();
        double bomberY = EntitiesManager.getInstance().players.get(0).getY();

        int jBomber = (int) (bomberX + GameConfig.TILE_SIZE / 2) / GameConfig.TILE_SIZE;
        int iBomber = (int) (bomberY + GameConfig.TILE_SIZE / 2) / GameConfig.TILE_SIZE;

        int ran = r.nextInt(directionList.size());
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
        Queue<Integer> tileI = new LinkedList<>();
        Queue<Integer> tileJ = new LinkedList<>();

        checkPass[i][j] = true;
        if (canMoveR && !checkPass[i][j + 1]) {
            checkPass[i][j + 1] = true;
            direc.add(Direction.RIGHT);
            tileI.add(i);
            tileJ.add(j + 1);
        }
        if (canMoveL && !checkPass[i][j - 1]) {
            checkPass[i][j - 1] = true;
            direc.add(Direction.LEFT);
            tileI.add(i);
            tileJ.add(j - 1);
        }
        if (canMoveU && !checkPass[i - 1][j]) {
            checkPass[i - 1][j] = true;
            direc.add(Direction.UP);
            tileI.add(i - 1);
            tileJ.add(j);
        }
        if (canMoveD && !checkPass[i + 1][j]) {
            checkPass[i + 1][j] = true;
            direc.add(Direction.DOWN);
            tileI.add(i + 1);
            tileJ.add(j);
        }

        while (!direc.isEmpty()) {
            Direction direction = direc.poll();
            i = tileI.poll();
            j = tileJ.poll();
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
                tileI.add(i);
                tileJ.add(j + 1);
            }
            if (canMoveL && !checkPass[i][j - 1]) {
                checkPass[i][j - 1] = true;
                direc.add(direction);
                tileI.add(i);
                tileJ.add(j - 1);
            }
            if (canMoveU && !checkPass[i - 1][j]) {
                checkPass[i - 1][j] = true;
                direc.add(direction);
                tileI.add(i - 1);
                tileJ.add(j);
            }
            if (canMoveD && !checkPass[i + 1][j]) {
                checkPass[i + 1][j] = true;
                direc.add(direction);
                tileI.add(i + 1);
                tileJ.add(j);
            }
        }
        return directionList.get(ran);
    }
}