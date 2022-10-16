package uet.oop.bomberman.components.entities.enemy;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Direction;

public class Minvo extends Enemy{
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

            if (Math.abs(jPlayer - j) < 3 && Math.abs(iPlayer - i) < 3) {
                if (jPlayer < j && canMoveL) {
                    moveX = -speed;
                } else if (jPlayer > j && canMoveR) {
                    moveX = speed;
                } else if (iPlayer < i && canMoveU) {
                    moveY = -speed;
                } else if (iPlayer > i && canMoveD) {
                    moveY = speed;
                }
            } else {
                checkMove();
                if(directionList.size() != 0){
                    int ran = r.nextInt(directionList.size());
                    if (directionList.get(ran) == Direction.UP) {
                        lastDirection = Direction.UP;
                    }
                    if (directionList.get(ran) == Direction.DOWN) {
                        lastDirection = Direction.DOWN;
                    }
                    if (directionList.get(ran) == Direction.RIGHT) {
                        lastDirection = Direction.RIGHT;
                        randomAnimation = false;
                    }
                    if (directionList.get(ran) == Direction.LEFT) {
                        lastDirection = Direction.LEFT;
                        randomAnimation = true;
                    }
                }
            }
        }
        x += moveX;
        y += moveY;
    }
}
