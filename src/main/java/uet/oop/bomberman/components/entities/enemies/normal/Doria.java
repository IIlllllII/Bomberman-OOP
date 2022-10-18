package uet.oop.bomberman.components.entities.enemies.normal;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;

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
        double bomberX = EntitiesManager.getInstance().players.get(0).getX();
        double bomberY = EntitiesManager.getInstance().players.get(0).getY();

        int j = (int) ((x + 12) / 32);
        int i = (int) ((y + 16) / 32);
        int jPlayer = (int) (bomberX + 16) / 32;
        int iPlayer = (int) (bomberY + 16) / 32;

        if (j * 32 == x && i * 32 == y) {
            moveX = 0;
            moveY = 0;
            canMoveR = checkMapHash(i, j + 1);
            canMoveL = checkMapHash(i, j - 1);
            canMoveU = checkMapHash(i - 1, j);
            canMoveD = checkMapHash(i + 1, j);

            if (Math.abs(jPlayer - j) >= Math.abs(iPlayer - i)){
                if(jPlayer >= j){
                    if(canMoveR){
                        lastDirection = Direction.RIGHT;
                    }else {
                        int ran = r.nextInt(directionList.size());
                        lastDirection = directionList.get(ran);
                    }
                }else {
                    if(canMoveL){
                        lastDirection = Direction.LEFT;
                    }else {
                        int ran = r.nextInt(directionList.size());
                        lastDirection = directionList.get(ran);
                    }
                }
            } else {
                if(iPlayer >= i){
                    if(canMoveD){
                        lastDirection = Direction.DOWN;
                    }else {
                        int ran = r.nextInt(directionList.size());
                        lastDirection = directionList.get(ran);
                    }
                }else {
                    if(canMoveU){
                        lastDirection = Direction.UP;
                    }else {
                        int ran = r.nextInt(directionList.size());
                        lastDirection = directionList.get(ran);
                    }
                }
            }
            checkMove();
        }
        x += moveX;
        y += moveY;
    }

    @Override
    protected boolean checkMapHash(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();
        if (i < 0 || i > (levelMap.getHeight() / 32) - 1 || j < 0 || j > (levelMap.getWidth() / 32) - 1) {
            return false;
        }
        return levelMap.getHashAt(i, j) == levelMap.getHash("grass")
                || levelMap.getHashAt(i, j) == levelMap.getHash("brick");
    }
}
