package uet.oop.bomberman.components.entities.bomber;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.items.item_types.Coin;
import uet.oop.bomberman.components.entities.materials.Portal;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Action;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;

import java.util.*;

public class AutoPlay extends Bomber {
    private boolean canMoveR = false;
    private boolean canMoveL = false;
    private boolean canMoveU = false;
    private boolean canMoveD = false;
    private Direction currentDirectionEnemy ;
    private double moveX = 0;
    private double moveY = 0;
    private int iBomber;
    private int jBomber;
    private final Random r = new Random();
    private final List<Direction> directionList = new ArrayList<>();

    public AutoPlay(double x, double y, int w, int h) {
        super(x, y, w, h);
        initDirectionList();
        iBomber = (int) (y) / GameConfig.TILE_SIZE;
        jBomber = (int) (x) / GameConfig.TILE_SIZE;
        lives = 5;
    }

    @Override
    public void move() {
        iBomber = (int) (bomberBox.getY()) / GameConfig.TILE_SIZE;
        jBomber = (int) (bomberBox.getX() + bomberBox.getWidth() / 2.0) / GameConfig.TILE_SIZE;

        if (Math.abs((double) jBomber * GameConfig.TILE_SIZE - x) < speed
                && Math.abs((double) iBomber * GameConfig.TILE_SIZE - y - 10) < speed
                && playerAction != Action.DEAD) {
            playerAction = Action.MOVING;
            if (EntitiesManager.getInstance().bombs.size() == 0) {
                if (checkAppearItem()) {
                    currentDirection = findEntity("item");
                } else if (EntitiesManager.getInstance().enemies.size() != 0) {
                    currentDirection = findEntity("enemy");
                    avoidEnemy();
                } else if (!EntitiesManager.getInstance().portal.isAppear()) {
                    currentDirection = findEntity("brick");
                } else if (EntitiesManager.getInstance().coins.size() == 0) {
                    currentDirection = findEntity("portal");
                } else {
                    currentDirection = findEntity("coin");
                }
                if(playerAction == Action.MOVING){
                    moveFind();
                }
            }
            if (EntitiesManager.getInstance().bombs.size() != 0) {
                if (avoidEnemy() && avoidBomb()) {
                    playerAction = Action.IDLE;
                }
                if (playerAction == Action.MOVING) {
                    moveAvoidBomb();
                }
            }
        }
        if (playerAction != Action.MOVING) {
            moveX = 0;
            moveY = 0;
        }
        x += moveX;
        y += moveY;
        updateBoxCollider();
    }

    private Direction findEntity(String nameEntity) {
        int i = iBomber;
        int j = jBomber;
        LevelMap levelMap = LevelMap.getInstance();
        boolean[][] checkPass =
                new boolean[levelMap.getMapHash().length][levelMap.getMapHash()[0].length];

        for (int m = 0; m < checkPass.length; m++) {
            for (int n = 0; n < checkPass[0].length; n++) {
                checkPass[m][n] = false;
            }
        }

        canMoveR = checkFindWay(i, j + 1);
        canMoveL = checkFindWay(i, j - 1);
        canMoveU = checkFindWay(i - 1, j);
        canMoveD = checkFindWay(i + 1, j);

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
            Direction direction1 = direc.poll();
            i = iTile.poll();
            j = jTile.poll();

            if (checkEntity(i, j, nameEntity)) {
                return direction1;
            }

            canMoveR = checkFindWay(i, j + 1);
            canMoveL = checkFindWay(i, j - 1);
            canMoveU = checkFindWay(i - 1, j);
            canMoveD = checkFindWay(i + 1, j);

            if (canMoveR && !checkPass[i][j + 1]) {
                checkPass[i][j + 1] = true;
                direc.add(direction1);
                iTile.add(i);
                jTile.add(j + 1);
            }
            if (canMoveL && !checkPass[i][j - 1]) {
                checkPass[i][j - 1] = true;
                direc.add(direction1);
                iTile.add(i);
                jTile.add(j - 1);
            }
            if (canMoveU && !checkPass[i - 1][j]) {
                checkPass[i - 1][j] = true;
                direc.add(direction1);
                iTile.add(i - 1);
                jTile.add(j);
            }
            if (canMoveD && !checkPass[i + 1][j]) {
                checkPass[i + 1][j] = true;
                direc.add(direction1);
                iTile.add(i + 1);
                jTile.add(j);
            }
        }
        return Direction.DOWN;
    }

    private void initDirectionList() {
        directionList.clear();
        directionList.add(Direction.LEFT);
        directionList.add(Direction.RIGHT);
        directionList.add(Direction.UP);
        directionList.add(Direction.DOWN);
    }

    private boolean checkEntity(int i, int j, String nameEntity) {
        switch (nameEntity) {
            case "enemy": {
                List<Enemy> enemyList = EntitiesManager.getInstance().enemies;
                if (enemyList == null || enemyList.size() == 0) {
                    return false;
                }
                for (Enemy enemy : enemyList) {
                    Direction directionEnemy = enemy.getCurrentDirection();
                    int iEnemy = (int) enemy.getY() / GameConfig.TILE_SIZE;
                    int jEnemy = (int) enemy.getX() / GameConfig.TILE_SIZE;
                    int mapWidth = LevelMap.getInstance().getMapHash()[0].length;
                    int mapHeight = LevelMap.getInstance().getMapHash().length;
                    if (directionEnemy == null) {
                        continue;
                    }
                    switch (directionEnemy) {
                        case UP:
                            iEnemy = iEnemy - 4 > 0 ? iEnemy - 4 : 1;
                            break;
                        case DOWN:
                            iEnemy = iEnemy + 4 < mapHeight - 1 ? iEnemy + 4 : mapHeight - 2;
                            break;
                        case LEFT:
                            jEnemy = jEnemy - 4 > 0 ? jEnemy - 4 : 1;
                            break;
                        case RIGHT:
                            jEnemy = jEnemy + 4 < mapWidth - 1 ? jEnemy + 4 : mapWidth - 2;
                        default:
                            break;
                    }
                    if (iEnemy == i && jEnemy == j) {
                        if(LevelMap.getInstance().getHashAt(iEnemy, jEnemy) != LevelMap.getInstance().getHash("grass")){
                            return false;
                        }
                        currentDirectionEnemy = directionEnemy;
                        return true;
                    }
                }
                return false;
            }
            case "portal": {
                Portal portal = EntitiesManager.getInstance().portal;
                int iPortal = (int) portal.getY() / GameConfig.TILE_SIZE;
                int jPortal = (int) portal.getX() / GameConfig.TILE_SIZE;
                return i == iPortal && j == jPortal;
            }
            case "brick": {
                LevelMap levelMap = LevelMap.getInstance();
                return levelMap.getHashAt(i, j) == levelMap.getHash("brick");
            }
            case "coin": {
                List<Coin> coinList = EntitiesManager.getInstance().coins;
                for (Coin coin : coinList) {
                    int iEnemy = (int) coin.getY() / GameConfig.TILE_SIZE;
                    int jEnemy = (int) coin.getX() / GameConfig.TILE_SIZE;
                    if (iEnemy == i && jEnemy == j && !coin.isEaten()) {
                        return true;
                    }
                }
                return false;
            }
            case "item": {
                for (Item item : EntitiesManager.getInstance().items) {
                    int iItem = (int) item.getY() / GameConfig.TILE_SIZE;
                    int jItem = (int) item.getX() / GameConfig.TILE_SIZE;
                    if (iItem == i && jItem == j && item.isAppear()) {
                        return true;
                    }
                }
                return false;
            }
            default:
                return false;
        }
    }

    private boolean avoidBomb() {
        for (Bomb bomb : EntitiesManager.getInstance().bombs) {
            int iBomb = (int) bomb.getY() / GameConfig.TILE_SIZE;
            int jBomb = (int) bomb.getX() / GameConfig.TILE_SIZE;
            if (jBomber == jBomb && iBomber == iBomb) {
                playerAction = Action.MOVING;
                if (directionList.size() != 0) {
                    int ran = r.nextInt(directionList.size());
                    currentDirection = directionList.get(ran);
                } else {
                    playerAction = Action.IDLE;
                }
                return false;
            }
            if (iBomber == iBomb && Math.abs(jBomber - jBomb) <= Bomb.getFlameLength()) {
                if (jBomber - jBomb > 0) {
                    directionList.remove(Direction.LEFT);
                } else {
                    directionList.remove(Direction.RIGHT);
                }
                if (directionList.size() != 0) {
                    int ran = r.nextInt(directionList.size());
                    currentDirection = directionList.get(ran);
                } else {
                    playerAction = Action.IDLE;
                }
                return false;
            }
            if (jBomber == jBomb && Math.abs(iBomber - iBomb) <= Bomb.getFlameLength()) {
                playerAction = Action.MOVING;
                if (iBomber - iBomb > 0) {
                    directionList.remove(Direction.UP);
                } else {
                    directionList.remove(Direction.DOWN);
                }
                if (directionList.size() != 0) {
                    int ran = r.nextInt(directionList.size());
                    currentDirection = directionList.get(ran);
                } else {
                    playerAction = Action.IDLE;
                }
                return false;
            }
        }
        return true;
    }

    private void moveAvoidBomb() {
        moveX = 0;
        moveY = 0;
        LevelMap levelMap = LevelMap.getInstance();
        canMoveR = levelMap.getHashAt(iBomber, jBomber + 1) == levelMap.getHash("grass");
        canMoveL = levelMap.getHashAt(iBomber, jBomber - 1) == levelMap.getHash("grass");
        canMoveU = levelMap.getHashAt(iBomber - 1, jBomber) == levelMap.getHash("grass");
        canMoveD = levelMap.getHashAt(iBomber + 1, jBomber) == levelMap.getHash("grass");
        switch (currentDirection) {
            case UP: {
                if (canMoveU) {
                    moveY = -speed;
                    initDirectionList();
                } else {
                    directionList.remove(currentDirection);
                }
                break;
            }
            case DOWN: {
                if (canMoveD) {
                    moveY = speed;
                    initDirectionList();
                } else {
                    directionList.remove(currentDirection);
                }
                break;
            }
            case LEFT: {
                if (canMoveL) {
                    moveX = -speed;
                    initDirectionList();
                } else {
                    directionList.remove(currentDirection);
                }
                break;
            }
            case RIGHT: {
                if (canMoveR) {
                    moveX = speed;
                    initDirectionList();
                } else {
                    directionList.remove(currentDirection);
                }
                break;
            }
            default:
                break;
        }
    }

    private void moveFind() {
        moveX = 0;
        moveY = 0;
        LevelMap levelMap = LevelMap.getInstance();
        if (checkEntity(iBomber, jBomber, "enemy")) {
            placeBomb();
            initDirectionList();
            switch (currentDirectionEnemy){
                case UP:
                    directionList.remove(Direction.DOWN);
                    break;
                case DOWN:
                    directionList.remove(Direction.UP);
                    break;
                case LEFT:
                    directionList.remove(Direction.RIGHT);
                    break;
                case RIGHT:
                    directionList.remove(Direction.LEFT);
                    break;
                default:
                    break;
            }
            return;
        }
        switch (currentDirection) {
            case UP: {
                if (levelMap.getHashAt(iBomber - 1, jBomber) == levelMap.getHash("grass")) {
                    moveY = -speed;
                } else if (levelMap.getHashAt(iBomber - 1, jBomber) == levelMap.getHash("brick")) {
                    placeBomb();
                    initDirectionList();
                } else if (checkEntity(iBomber - 1, jBomber, "portal")
                        && EntitiesManager.getInstance().portal.isCanPass()) {
                    levelMap.prepareNextLevel();
                    moveY = -speed;
                }
                break;
            }
            case DOWN: {
                if (levelMap.getHashAt(iBomber + 1, jBomber) == levelMap.getHash("grass")) {
                    moveY = speed;
                } else if (levelMap.getHashAt(iBomber + 1, jBomber) == levelMap.getHash("brick")) {
                    placeBomb();
                    initDirectionList();
                } else if (checkEntity(iBomber + 1, jBomber, "portal")
                        && EntitiesManager.getInstance().portal.isCanPass()) {
                    levelMap.prepareNextLevel();
                    moveY = speed;
                }
                break;
            }
            case LEFT: {
                if (levelMap.getHashAt(iBomber, jBomber - 1) == levelMap.getHash("grass")) {
                    moveX = -speed;
                } else if (levelMap.getHashAt(iBomber, jBomber - 1) == levelMap.getHash("brick")) {
                    placeBomb();
                    initDirectionList();
                } else if (checkEntity(iBomber, jBomber - 1, "portal")
                        && EntitiesManager.getInstance().portal.isCanPass()) {
                    levelMap.prepareNextLevel();
                    moveX = -speed;
                }
                break;
            }
            case RIGHT: {
                if (levelMap.getHashAt(iBomber, jBomber + 1) == levelMap.getHash("grass")) {
                    moveX = speed;
                } else if (levelMap.getHashAt(iBomber, jBomber + 1) == levelMap.getHash("brick")) {
                    placeBomb();
                    initDirectionList();
                } else if (checkEntity(iBomber, jBomber + 1, "portal")
                        && EntitiesManager.getInstance().portal.isCanPass()) {
                    levelMap.prepareNextLevel();
                    moveX = speed;
                }
                break;
            }
            default:
                break;
        }
    }

    private boolean avoidEnemy() {
        for (Enemy enemy : EntitiesManager.getInstance().enemies) {
            int iEnemy = (int) enemy.getY() / GameConfig.TILE_SIZE;
            int jEnemy = (int) enemy.getX() / GameConfig.TILE_SIZE;
            if (iBomber == iEnemy && Math.abs(jBomber - jEnemy) <= 2) {
                if (jBomber - jEnemy > 0) {
                    directionList.remove(Direction.LEFT);
                } else {
                    directionList.remove(Direction.RIGHT);
                }
                if (directionList.size() != 0) {
                    int ran = r.nextInt(directionList.size());
                    currentDirection = directionList.get(ran);
                } else {
                    playerAction = Action.IDLE;
                }
                return false;
            }
            if (jBomber == jEnemy && Math.abs(iBomber - iEnemy) <= 2) {
                if (iBomber - iEnemy > 0) {
                    directionList.remove(Direction.UP);
                } else {
                    directionList.remove(Direction.DOWN);
                }
                if (directionList.size() != 0) {
                    int ran = r.nextInt(directionList.size());
                    currentDirection = directionList.get(ran);
                } else {
                    playerAction = Action.IDLE;
                }
                return false;
            }
            if (Math.abs(iBomber - iEnemy) == 1 && Math.abs(jBomber - jEnemy) == 1) {
                playerAction = Action.IDLE;
                return true;
            }
        }
        return true;
    }

    private boolean checkFindWay(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();
        if (levelMap.getHashAt(i, j) == levelMap.getHash("wall")) {
            return false;
        }
        if (levelMap.getHashAt(i, j) == levelMap.getHash("portal")) {
            return !EntitiesManager.getInstance().portal.isAppear()
                    || EntitiesManager.getInstance().portal.isCanPass();
        }
        return true;
    }

    private boolean checkAppearItem() {
        for (Item item : EntitiesManager.getInstance().items) {
            if (item.isAppear()) {
                return true;
            }
        }
        return false;
    }
}
