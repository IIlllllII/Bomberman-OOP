package uet.oop.bomberman.components.entities;

import uet.oop.bomberman.components.entities.bomb.Bomb;
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
    public List<Brick> brokenBricks = new ArrayList<>();
    public List<Entity> items = new ArrayList<>();
    private EntitiesManager() {}

    private static class SingletonHelper {
        private static final EntitiesManager INSTANCE = new EntitiesManager();
    }

    public static EntitiesManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Renew all entities when switching to another level
     */
    public void renewEntities() {
        //players.clear();
        bombs.clear();
        brokenBricks.clear();
        items.clear();
    }
}
