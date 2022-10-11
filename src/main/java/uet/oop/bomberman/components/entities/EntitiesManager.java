package uet.oop.bomberman.components.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.entities.stillobjects.Brick;

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
    public List<Entity> enemy = new ArrayList<>();
    
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
        enemy.forEach(enemy -> enemy.render(gc));
        players.forEach(player -> player.render(gc));
    }

    public void update() {
        players.forEach(Entity::update);
        bricks.forEach(Brick::update);
        enemy.forEach(Entity::update);
        items.forEach(Entity::update);

        for (int i = 0; i < bombs.size(); i++){
            if (! bombs.get(i).isDone()) {
                bombs.get(i).update();
            } else {
                bombs.remove(i);
                i--;
            }
        }
    }

    /**
     * Renew all entities when switching to another level
     */
    public void renewEntities() {
        players.clear();
        bombs.clear();
        bricks.clear();
        items.clear();
        enemy.clear();
    }
}
