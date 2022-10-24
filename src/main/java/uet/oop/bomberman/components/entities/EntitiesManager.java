package uet.oop.bomberman.components.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.entities.enemies.bosses.Banana;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.items.item_types.Coin;
import uet.oop.bomberman.components.entities.bomber.Bomber;
import uet.oop.bomberman.components.entities.materials.Brick;
import uet.oop.bomberman.components.entities.materials.Portal;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.config.CharacterStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * This class applies `Singleton pattern`
 * with Bill Pugh Singleton Implementation.
 * <p>
 * For more information:
 * https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
 */
public class EntitiesManager {
    public List<Bomber> bombers = new ArrayList<>();
    public List<Bomb> bombs = new ArrayList<>();
    public List<Brick> bricks = new ArrayList<>();
    public List<Item> items = new ArrayList<>();
    public List<Coin> coins = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    public Portal portal = new Portal(0, 0);

    private EntitiesManager() {
    }

    private static class SingletonHelper {
        private static final EntitiesManager INSTANCE = new EntitiesManager();
    }

    public static EntitiesManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void render(GraphicsContext gc) {
        items.forEach(item -> item.render(gc));
        portal.render(gc);
        bombs.forEach(entity -> entity.render(gc));
        bricks.forEach(entity -> entity.render(gc));
        coins.forEach(entity -> entity.render(gc));
        enemies.forEach(enemy -> {
            if (!(enemy instanceof Banana)) {
                enemy.render(gc);
            }
        });
        bombers.forEach(player -> player.render(gc));
        enemies.forEach(enemy -> {
            if (enemy instanceof Banana) {
                enemy.render(gc);
            }
        });
    }

    public void update() {
        bombers.forEach(Entity::update);
        checkCollision();

        bricks.forEach(Brick::update);
        enemies.forEach(Entity::update);
        items.forEach(Entity::update);
        coins.forEach(Coin::update);

        if (enemies.size() == 0) {
            portal.setCanPass(true);
        }
        for (int i = 0; i < bombs.size(); i++) {
            if (!bombs.get(i).isDone()) {
                bombs.get(i).update();
            } else {
                LevelMap.getInstance().setHashAt(
                        (int) bombs.get(i).getY() / GameConfig.TILE_SIZE,
                        (int) bombs.get(i).getX() / GameConfig.TILE_SIZE,
                        "grass");
                bombs.remove(i);
                i--;
            }
        }
    }

    private void checkCollision() {
        BoxCollider bomberBox = bombers.get(0).getBomberBox();

        items.forEach(item -> {
            if (item.isAppear()) {
                BoxCollider itemBox = new BoxCollider(item.getX(), item.getY());
                if (bomberBox.isCollidedWith(itemBox)) {
                    item.setEaten(true);
                }
            }
        });

        coins.forEach(coin -> {
            if (coin.isAppear()) {
                BoxCollider coinBox = new BoxCollider(coin.getX(), coin.getY());
                if (bomberBox.isCollidedWith(coinBox)) {
                    coin.setEaten(true);
                    //coin.setAppear(false);
                }
            }
        });

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDone()) {
                enemies.remove(i);
                i--;
            } else {
                BoxCollider enemyBox;
                if (enemy instanceof Banana) {
                    enemyBox = ((Banana) enemy).getDeathBox();
                } else {
                    enemyBox = new BoxCollider(enemy.getX(), enemy.getY(), 30, 30);
                }
                if (!enemy.isDestroyed() && bomberBox.isCollidedWith(enemyBox) && !bombers.get(0).isInvincible()) {
                    bombers.get(0).setPlayerStatus(CharacterStatus.DEAD);
                }
            }
        }

        bombs.forEach(bomb -> {
            bomb.getFlameList().forEach(flame -> {
                if (! flame.isDone()) {
                    BoxCollider flameBox = new BoxCollider(flame.getX(), flame.getY());

                    if (bomberBox.isCollidedWith(flameBox) && !bombers.get(0).isCanPassFlame()
                            && !bombers.get(0).isInvincible()) {
                        bombers.get(0).setPlayerStatus(CharacterStatus.DEAD);
                    }

                    enemies.forEach(enemy -> {
                        if (enemy instanceof Banana) {
                            BoxCollider enemyBox = ((Banana) enemy).getDeathBox();
                            if (!enemy.isDestroyed() && enemyBox.isCollidedWith(flameBox)) {
                                ((Banana) enemy).decreaseLives();
                            }
                        } else {
                            BoxCollider enemyBox = new BoxCollider(enemy.getX(), enemy.getY(), 30, 30);
                            if (!enemy.isDestroyed() && enemyBox.isCollidedWith(flameBox)) {
                                enemy.setDestroyed(true);
                            }
                        }
                    });
                }
            });
        });
    }

    /**
     * Renew all entities when switching to another level
     */
    public void renewEntities() {
//        players.clear();
        bombs.clear();
        bricks.clear();
        items.clear();
        coins.clear();
        enemies.clear();
        portal = new Portal(0, 0);
        portal.setAppear(false);
    }
}
