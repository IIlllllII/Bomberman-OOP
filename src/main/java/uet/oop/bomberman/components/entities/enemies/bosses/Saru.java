package uet.oop.bomberman.components.entities.enemies.bosses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.bomb.Flame;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Action;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.algo.AStar;
import uet.oop.bomberman.core.algo.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Saru extends Enemy {
    private final Map<String, Animation> animationDict = new HashMap<>();
    private final int visionRange = 3; //Cell unit.
    private int lives;
    private Action saruAction;
    private boolean suicide;
    private boolean canMove;
    private LinkedList<Node> path;

    private AStar pathFinder = new AStar();

    public Saru(double x, double y) {
        super(x, y);
        animationDict.put("down",
                new Animation(SpriteSheet.saru, 2, 2, 1000, 5, 48,
                        21, 18, 21 * 1.5f, 18 * 1.5f, 5f, false));

        animationDict.put("up",
                new Animation(SpriteSheet.saru, 2, 2, 1000, 4, 95,
                        21, 18, 21 * 1.5f, 18 * 1.5f, 6f, false));

        animationDict.put("right",
                new Animation(SpriteSheet.saru, 2, 2, 1000, 3, 71,
                        21, 18, 21 * 1.6f, 18 * 1.6f, 9f, false));

        animationDict.put("left",
                new Animation(SpriteSheet.saru, 2, 2, 1000, 3, 71,
                        21, 18, 21 * 1.6f, 18 * 1.6f, 9f, true));

        animationDict.put("idle",
                new Animation(SpriteSheet.saru, 4, 4, 1000, 3, 3,
                        24, 19, 24 * 1.5f, 19 * 1.5f, 3f, false));

        animationDict.put("dead",
                new Animation(SpriteSheet.saru, 4, 4, 1000, 57, 71,
                        23, 19, 23 * 1.5f, 19 * 1.5f, 4f, false));

        animationDict.get("down").setLoop(true);
        animationDict.get("up").setLoop(true);
        animationDict.get("left").setLoop(true);
        animationDict.get("right").setLoop(true);
        animationDict.get("idle").setLoop(true);
        animationDict.get("dead").setLoop(false);

        initDirectionList();
        saruAction = Action.IDLE;
        currentDirection = Direction.values()[random.nextInt(Direction.values().length)];
        score = 10000;
        speed = 4;
        lives = 3;
        suicide = false;
        canMove = true;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            if (saruAction == Action.IDLE) {
                animationDict.get("idle").render(gc, x - camera.getX(), y - camera.getY());
            } else {
                animationDict.get(currentDirection.label).render(gc, x - camera.getX(), y - camera.getY());
            }
        } else {
            animationDict.get("dead").render(gc, x - camera.getX(), y - camera.getY());
            if (! animationDict.get("dead").isDone()) {
                Text text = new Text();
                gc.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
                gc.setFill(Color.SNOW);
                gc.fillText(" + " + score,
                        x - camera.getX() + 16,
                        y - camera.getY() + 20 - animationDict.get("dead").getCalcTime() / 32);
            } else {
                done = true;
            }
        }
    }

    @Override
    public void update() {
        if (destroyed) {
            animationDict.get("dead").update();
            return;
        }
        if (saruAction == Action.IDLE) {
            int centerX = (int) this.x + GameConfig.TILE_SIZE / 2;
            int centerY = (int) this.y + GameConfig.TILE_SIZE / 2;
            this.x = (double) (centerX / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
            this.y = (double) (centerY / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
            animationDict.get("idle").update();
        } else {
            animationDict.get(currentDirection.label).update();
        }

        canMove = updateCanMove();
        if (canMove) {
            //avoidBomb();
            move();
        }
    }
    private boolean updateCanMove() {
        //Saru can only move when Banana(s) are existing.
        List<Enemy> enemies = EntitiesManager.getInstance().enemies;
        for (Enemy enemy : enemies) {
            if (enemy instanceof Banana) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDone() {
        return animationDict.get("dead").isDone();
    }

    private void avoidBomb() {
        if (path != null) {
            return;
        }
        int cellX = (int) x / GameConfig.TILE_SIZE;
        int cellY = (int) y / GameConfig.TILE_SIZE;

        List<Bomb> bombList = EntitiesManager.getInstance().bombs;
        for (Bomb bomb : bombList) {
            int bombCellX = (int) bomb.getX() / GameConfig.TILE_SIZE;
            int bombCellY = (int) bomb.getY() / GameConfig.TILE_SIZE;

            if ((cellX == bombCellX && Math.abs(cellY - bombCellY) <= Flame.MAX_LENGTH)
                || (cellY == bombCellY && Math.abs(cellX - bombCellX) <= Flame.MAX_LENGTH)) {
                //In danger, find a safe position to move in:
                int delta = 0;
                path = null;
                Node start = new Node(cellY, cellX);
                while (true) {
                    delta++;
                    path = pathFinder.findPath(start, new Node(cellY + delta, cellX));
                    if (path != null && !path.isEmpty()) {
                        break;
                    }
                    path = pathFinder.findPath(start, new Node(cellY - delta, cellX));
                    if (path != null && !path.isEmpty()) {
                        break;
                    }
                    path = pathFinder.findPath(start, new Node(cellY, cellX + delta));
                    if (path != null && !path.isEmpty()) {
                        break;
                    }
                    path = pathFinder.findPath(start, new Node(cellY, cellX - delta));
                    if (path != null && !path.isEmpty()) {
                        break;
                    }
                    if (delta >= 5) {
                        break;
                    }
                }
                if (path != null) {
//                    System.out.println("In danger: " + path.isEmpty());
//                    for (Node node : path) {
//                        if (node != null) {
//                            System.out.print("(" + node.getRow() + "," + node.getCol() + ") -> ");
//                        }
//                    }
                    break;
                }
            }
        }
    }

    @Override
    protected void move() {
        double bomberX = EntitiesManager.getInstance().bombers.get(0).getX();
        double bomberY = EntitiesManager.getInstance().bombers.get(0).getY();
        int bomberWidth = EntitiesManager.getInstance().bombers.get(0).getWidth();
        int bomberHeight = EntitiesManager.getInstance().bombers.get(0).getHeight();
        int cellXPlayer = (int) ((bomberX + bomberWidth / 2) / GameConfig.TILE_SIZE);
        int cellYPlayer = (int) ((bomberY + bomberHeight / 2) / GameConfig.TILE_SIZE);

        int centerX = (int) this.x + GameConfig.TILE_SIZE / 2;
        int centerY = (int) this.y + GameConfig.TILE_SIZE / 2;
        int cellX = centerX / GameConfig.TILE_SIZE;
        int cellY = centerY / GameConfig.TILE_SIZE;


        canMoveR = checkMapHash(cellY, cellX + 1);
        canMoveL = checkMapHash(cellY, cellX - 1);
        canMoveU = checkMapHash(cellY - 1, cellX);
        canMoveD = checkMapHash(cellY + 1, cellX);


        if (suicide) {
            saruAction = Action.MOVING;
            checkMove();
            x += moveX;
            y += moveY;
            if (cellX == cellXPlayer && cellY == cellYPlayer) {
                suicide = false;
                saruAction = Action.IDLE;
            }
            return;
        }

//        if (path != null && !path.isEmpty()) {
//            saruAction = Action.MOVING;
//            moveX = 0;
//            moveY = 0;
//            Node thisNode = path.removeFirst();
//            int deltaRow = thisNode.getRow() - cellY;
//            int deltaCol = thisNode.getCol() - cellX;
//
//            if (deltaRow == 0 && deltaCol == 0) {
//                return;
//            }
//
//            if (deltaRow > 0) {
//                currentDirection = Direction.DOWN;
//                canMoveD = true;
//            } else {
//                currentDirection = Direction.UP;
//                canMoveU = true;
//            }
//
//            if (deltaCol > 0) {
//                currentDirection = Direction.RIGHT;
//                canMoveR = true;
//            } else {
//                currentDirection = Direction.LEFT;
//                canMoveL = true;
//            }
//            checkMove();
////            System.out.println("Can move: " + canMoveL + " " + canMoveR + " " + canMoveU + " " + canMoveD);
////            System.out.println(moveX + " & " + moveY);
//            x += moveX;
//            y += moveY;
//            return;
//        }

        if (Math.abs(cellXPlayer - cellX) <= visionRange && Math.abs(cellYPlayer - cellY) <= visionRange
            && EntitiesManager.getInstance().bombers.get(0).getPlayerAction() != Action.DEAD) {
            moveX = 0;
            moveY = 0;

            if (cellXPlayer == cellX) {
                if (cellYPlayer > cellY) {
                    currentDirection = Direction.DOWN;
                } else {
                    currentDirection = Direction.UP;
                }
                suicide = true;
                saruAction = Action.MOVING;
                if (currentDirection == Direction.UP && !canMoveU || currentDirection == Direction.DOWN && !canMoveD) {
                    suicide = false;
                    saruAction = Action.IDLE;
                }

            }
            if (cellYPlayer == cellY) {
                if (cellXPlayer > cellX) {
                    currentDirection = Direction.RIGHT;
                } else {
                    currentDirection = Direction.LEFT;
                }
                suicide = true;
                saruAction = Action.MOVING;
                if (currentDirection == Direction.RIGHT && !canMoveR || currentDirection == Direction.LEFT && !canMoveL) {
                    suicide = false;
                    saruAction = Action.IDLE;
                }
            }
        } else {
            saruAction = Action.IDLE;
        }
    }
}
