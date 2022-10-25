package uet.oop.bomberman.components.entities.bomber;

import javafx.scene.input.KeyCode;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Action;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;

import java.util.List;

public class Player extends Bomber {
    public Player(double x, double y, int w, int h){
        super(x, y, w, h);
        updateBoxCollider();
    }
    public void handleInput(List<KeyCode> inputList) {
        if (getPlayerAction() == Action.DEAD) {
            return;
        }

        Direction direction = null;
        if (inputList.contains(KeyCode.RIGHT) || inputList.contains(KeyCode.D)) {
            direction = Direction.RIGHT;
        }
        if (inputList.contains(KeyCode.LEFT) || inputList.contains(KeyCode.A)) {
            direction = Direction.LEFT;
        }
        if (inputList.contains(KeyCode.UP) || inputList.contains(KeyCode.W)) {
            direction = Direction.UP;
        }

        if (inputList.contains(KeyCode.DOWN) || inputList.contains(KeyCode.S)) {
            direction = Direction.DOWN;
        }

        if (inputList.contains(KeyCode.SPACE)) {
            placeBomb();
            inputList.remove(KeyCode.SPACE);
        }

        if (direction != null) {
            playerAction = Action.MOVING;
            currentDirection = direction;
        } else {
            playerAction = Action.IDLE;
        }
    }

    @Override
    public void move() {
        //Note: `steps` is always positive at first.
        double steps = speed;
        if (playerAction == Action.IDLE || playerAction == Action.DEAD) {
            return;
        }

        switch (currentDirection) {
            case DOWN:
                y += steps;
                break;
            case UP:
                steps = -steps;
                y += steps;
                break;
            case RIGHT:
                x += steps;
                break;
            case LEFT:
                steps = -steps;
                x += steps;
                break;
        }

        LevelMap levelMap = LevelMap.getInstance();
        if ((x < 0) || (x + width > levelMap.getWidth())) {
            x -= steps;     //Move back
        }

        if ((y < 0) || (y + height > levelMap.getHeight())) {
            y -= steps;     //Move back
        }

        updateBoxCollider();

        int leftCol = (int) bomberBox.getX() / GameConfig.TILE_SIZE;
        int rightCol = (int) (bomberBox.getX() + bomberBox.getWidth()) / GameConfig.TILE_SIZE;
        int topRow = (int) bomberBox.getY() / GameConfig.TILE_SIZE;
        int bottomRow = (int) (bomberBox.getY() + bomberBox.getHeight()) / GameConfig.TILE_SIZE;

        //Barrier checker.
        boolean topLeftCheck = checkBarrier(topRow, leftCol);
        boolean topRightCheck = checkBarrier(topRow, rightCol);
        boolean bottomLeftCheck = checkBarrier(bottomRow, leftCol);
        boolean bottomRightCheck = checkBarrier(bottomRow, rightCol);

        switch (currentDirection) {
            case UP:
                if (topLeftCheck || topRightCheck) {
                    y -= steps;
                }
                break;
            case DOWN:
                if (bottomLeftCheck || bottomRightCheck) {
                    y -= steps;
                }
                break;
            case RIGHT:
                if (topRightCheck || bottomRightCheck) {
                    x -= steps;
                }
                break;
            case LEFT:
                if (topLeftCheck || bottomLeftCheck) {
                    x -= steps;
                }
                break;
        }
    }

    private boolean checkBarrier(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();

        if (levelMap.getHashAt(i, j) == levelMap.getHash("bomb")) {
            if (EntitiesManager.getInstance().bombs.
                    get(EntitiesManager.getInstance().bombs.size() - 1).isAllowPass()) {
                return false;
            }
            return !canPassBomb;
        }
        if (levelMap.getHashAt(i, j) == levelMap.getHash("brick")) {
            return !canPassBrick;
        }

        if (levelMap.getHashAt(i, j) == levelMap.getHash("portal")) {
            if (EntitiesManager.getInstance().portal.isCanPass()) {
                levelMap.prepareNextLevel();
                return false;
            } else {
                return true;
            }
        }

        return levelMap.getHashAt(i, j) == levelMap.getHash("wall");
    }
}
