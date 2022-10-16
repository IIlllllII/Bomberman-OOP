package uet.oop.bomberman.components.entities.enemy;

import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Direction;

public class Pontan extends  Enemy{
    public Pontan(double x, double y){
        super(x, y);
        animationLeft = new Animation(SpriteSheet.enemy, 3, 3, 1000, 0 * 32, 2 * 32, 32, 32);
        animationRight = new Animation(SpriteSheet.enemy, 3, 3, 1000, 3 * 32, 2 * 32, 32, 32);
        animationDeath = new Animation(SpriteSheet.enemy, 4, 4, 1000, 6 * 32, 2 * 32, 32, 32);
        animationLeft.setLoop(true);
        animationRight.setLoop(true);
        initDirectionList();

        score = 2000;
        speed = 4;
    }

    @Override
    protected void move() {
        int j = (int) (x / 32);
        int i = (int) (y / 32);

        if (j * 32 == x && i * 32 == y) {
            moveX = 0;
            moveY = 0;
            canMoveR = checkMapHash(i, j + 1);
            canMoveL = checkMapHash(i, j - 1);
            canMoveU = checkMapHash(i - 1, j);
            canMoveD = checkMapHash(i + 1, j);

            checkMove();
            if (directionList.size() != 0) {
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
        x += moveX;
        y += moveY;
    }
}
