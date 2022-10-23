package uet.oop.bomberman.components.entities.players;

import javafx.scene.input.KeyCode;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.CharacterStatus;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;

import java.util.List;

public class Player extends Bomber{
    public Player(double x, double y, int w, int h){
        super(x, y, w, h);
        updateBoxCollider();
        speed = 2;
    }
    public void handleInput(List<KeyCode> inputList) {
        if (playerStatus == CharacterStatus.DEAD) {
            return;
        }

        Direction currentDirection = null;
        if (inputList.contains(KeyCode.RIGHT) || inputList.contains(KeyCode.D)) {
            currentDirection = Direction.RIGHT;
        }
        if (inputList.contains(KeyCode.LEFT) || inputList.contains(KeyCode.A)) {
            currentDirection = Direction.LEFT;
        }
        if (inputList.contains(KeyCode.UP) || inputList.contains(KeyCode.W)) {
            currentDirection = Direction.UP;
        }

        if (inputList.contains(KeyCode.DOWN) || inputList.contains(KeyCode.S)) {
            currentDirection = Direction.DOWN;
        }

        if (inputList.contains(KeyCode.SPACE)) {
            placeBomb();
            inputList.remove(KeyCode.SPACE);
        }

        if (currentDirection != null) {
            playerStatus = CharacterStatus.MOVING;
            direction = currentDirection;
        } else {
            playerStatus = CharacterStatus.IDLE;
        }
    }

    @Override
    protected void updateBoxCollider() {
        bomberBox.setLocation(
                this.x + (this.width - bomberBox.getWidth()) / 2.0,
                this.y + bomberBox.getHeight() - 5
        );
    }

    @Override
    public void move() {
        //Note: `steps` is always positive at first.
        int steps = speed;
        if (playerStatus == CharacterStatus.IDLE) {
            return;
        }

        switch (direction) {
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

        switch (direction) {
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
