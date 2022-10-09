package uet.oop.bomberman.components.entities;

<<<<<<< HEAD:src/main/java/uet/oop/bomberman/core/EntitiesManager.java
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.bomb.Bomb;
=======
>>>>>>> 5d2e4bfbd8f825b5b9e8c77337522f942424bdf4:src/main/java/uet/oop/bomberman/components/entities/EntitiesManager.java
import uet.oop.bomberman.components.entities.players.Bomber;

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
    public List<Entity> animatedEntities = new ArrayList<>();
    public List<Entity> grasses = new ArrayList<>();
    public List<Entity> stillObjects = new ArrayList<>();
    public List<Entity> items = new ArrayList<>();
    public Bomb bomb ;
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
