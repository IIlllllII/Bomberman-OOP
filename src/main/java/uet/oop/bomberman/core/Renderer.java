package uet.oop.bomberman.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.config.GameScreen;

public class Renderer {
    public static void renderScreen(GameScreen screen) {
        GraphicsContext gc = GameManager.getGraphicsContext();
        Canvas canvas = GameManager.getCanvas();
        EntitiesManager entitiesManager = EntitiesManager.getInstance();

        if (screen == GameScreen.PLAYING) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            entitiesManager.grasses.forEach(entity -> entity.render(gc));
            entitiesManager.stillObjects.forEach(entity -> entity.render(gc));
            entitiesManager.players.forEach(entity -> entity.render(gc));
            entitiesManager.animatedEntities.forEach(entity -> entity.render(gc));
        } else {
            //Render background
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.drawImage(ScreenManager.getBackground(), 0, 0);
        }
    }

    public static void updateScreen(GameScreen screen) {
        EntitiesManager entitiesManager = EntitiesManager.getInstance();
        if (screen == GameScreen.PLAYING) {
            entitiesManager.players.forEach(Entity::update);
            entitiesManager.animatedEntities.forEach(Entity::update);
        }
    }
}
