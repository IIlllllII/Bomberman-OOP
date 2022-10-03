package uet.oop.bomberman.core;

import uet.oop.bomberman.components.entities.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class applies `Singleton pattern`
 * with Bill Pugh Singleton Implementation.
 *
 * For more information:
 * https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
 */
public class EntitiesManager {
    public List<Entity> players = new ArrayList<>();
    public List<Entity> animatedEntities = new ArrayList<>();
    public List<Entity> grasses = new ArrayList<>();
    public List<Entity> stillObjects = new ArrayList<>();
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
        players.clear();
        animatedEntities.clear();
        grasses.clear();
        stillObjects.clear();
        items.clear();
    }
}
