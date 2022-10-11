package uet.oop.bomberman.components.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.enemy.Balloom;
import uet.oop.bomberman.components.entities.enemy.Enemy;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.entities.stillobjects.Brick;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.config.PlayerStatus;

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
    public List<Bomber> players = new ArrayList<>();
    public List<Bomb> bombs = new ArrayList<>();
    public List<Brick> bricks = new ArrayList<>();
    public List<Item> items = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    
    private EntitiesManager() {}

    private static class SingletonHelper {
        private static final EntitiesManager INSTANCE = new EntitiesManager();
    }

    public static EntitiesManager getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    public void render(GraphicsContext gc) {
        items.forEach(items -> items.render(gc));
        bombs.forEach(entity -> entity.render(gc));
        bricks.forEach(entity -> entity.render(gc));
        enemies.forEach(enemy -> enemy.render(gc));
        players.forEach(player -> player.render(gc));
    }

    public void update() {
        players.forEach(Entity::update);
        checkCollision();

        bricks.forEach(Brick::update);
        enemies.forEach(Entity::update);
        items.forEach(Entity::update);

        for (int i = 0; i < bombs.size(); i++){
            if (! bombs.get(i).isDone()) {
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
        BoxCollider bomberBox = players.get(0).getBomberBox();

        items.forEach(item -> {
            BoxCollider itemBox = new BoxCollider(item.getX(), item.getY(), 32, 32);
            if (bomberBox.isCollidedWith(itemBox)) {
                if(item.isAppear()){
                    item.setEaten(true);
                }
            }
        });

        enemies.forEach(enemy -> {
            if (!enemy.isDestroyed() && bomberBox.isCollidedWith(new BoxCollider(
                enemy.getX(), enemy.getY(), 30, 30)) && !players.get(0).isInvincible()) {
                players.get(0).setPlayerStatus(PlayerStatus.DEAD);
            }
        });

        bombs.forEach(bomb -> {
            bomb.getFlameList().forEach(flame -> {
                BoxCollider flameBox = new BoxCollider(flame.getX(), flame.getY(), 32, 32);

                if (bomberBox.isCollidedWith(flameBox) && !players.get(0).isCanPassFlame()
                        && !players.get(0).isInvincible()) {
                    players.get(0).setPlayerStatus(PlayerStatus.DEAD);
                }

                enemies.forEach(enemy -> {
                    BoxCollider enemyBox = new BoxCollider(enemy.getX(), enemy.getY(), 30, 30);
                    if (!enemy.isDestroyed() && enemyBox.isCollidedWith(flameBox)) {
                        enemy.setDestroyed(true);
                    }
                });
            });
        });
    }

    /**
     * Renew all entities when switching to another level
     */
    public void renewEntities() {
        players.clear();
        bombs.clear();
        bricks.clear();
        items.clear();
        enemies.clear();
    }
}
