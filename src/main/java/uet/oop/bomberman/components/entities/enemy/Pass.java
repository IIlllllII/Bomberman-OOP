package uet.oop.bomberman.components.entities.enemy;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;

import java.util.LinkedList;
import java.util.Queue;

public class Pass extends Enemy {
    public Pass(double x, double y) {
        super(x, y);
        animationLeft = new Animation(SpriteSheet.enemy, 3, 3, 1000, 1 * 32, 6 * 32, 32, 32);
        animationRight = new Animation(SpriteSheet.enemy, 3, 3, 1000, 4 * 32, 6 * 32, 32, 32);
        animationDeath = new Animation(SpriteSheet.enemy, 4, 4, 1000, 7 * 32, 6 * 32, 32, 32);
        animationLeft.setLoop(true);
        animationRight.setLoop(true);
        initDirectionList();

        score = 2000;
        speed = 8 / 3;
    }

    @Override
    protected void move() {
        int j = (int) ((x + 12) / 32);
        int i = (int) ((y + 16) / 32);
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
        LevelMap levelMap = LevelMap.getInstance();
        boolean[][] checkPass =
                new boolean[levelMap.getMapHash().length][levelMap.getMapHash()[0].length];

        for (int m = 0; m < checkPass.length; m++) {
            for (int n = 0; n < checkPass[0].length; n++) {
                checkPass[m][n] = false;
            }
        }

        double bomberX = EntitiesManager.getInstance().players.get(0).getX();
        double bomberY = EntitiesManager.getInstance().players.get(0).getY();

        int jBomber = (int) (bomberX + 16) / 32;
        int iBomber = (int) (bomberY + 16) / 32;

        canMoveR = checkMapHash(i, j + 1);
        canMoveL = checkMapHash(i, j - 1);
        canMoveU = checkMapHash(i - 1, j);
        canMoveD = checkMapHash(i + 1, j);

        Queue<Direction> direc = new LinkedList<>();
        Queue<Integer> iTile = new LinkedList<>();
        Queue<Integer> jTile = new LinkedList<>();

        // Tránh bomb
        boolean checkBomb = false;
        for(Bomb bomb : EntitiesManager.getInstance().bombs){
            int iBomb = (int) bomb.getY() / GameConfig.TILE_SIZE;
            int jBomb = (int) bomb.getX() / GameConfig.TILE_SIZE;
            if((i == iBomb && Math.abs(j - jBomb) <= Bomb.getFlameLength() + 1) && j != jBomb){
                if(j - jBomb <= Bomb.getFlameLength() + 1){
                    directionList.remove(Direction.LEFT);
                }else {
                    directionList.remove(Direction.RIGHT);
                }
            }
            if(j == jBomb && Math.abs(i - iBomb) <= Bomb.getFlameLength() + 1 && i != iBomb){
                if(i - iBomb <= Bomb.getFlameLength() + 1){
                    directionList.remove(Direction.UP);
                }else {
                    directionList.remove(Direction.DOWN);
                }
            }
        }

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
