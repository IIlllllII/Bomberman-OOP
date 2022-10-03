package uet.oop.bomberman.core.scenes;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.EntitiesManager;

public class PlayScene {
    private final Group root;

    private final GraphicsContext gc;
    private final Canvas canvas;
    double x = GameConfig.WIDTH;
    double y = GameConfig.HEIGHT;

    public PlayScene() {
        canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root = new Group();

        Button playButton = new Button("BACK");
        playButton.setOnAction(event -> {
            SceneManager.getInstance().setCurrentScene(SceneManager.SCENES.MENU);
        });

        root.getChildren().addAll(canvas, playButton);

        EntitiesManager.getInstance().players.add(new Bomber(10, 10));
    }

    public Group getRoot() {
        return root;
    }

    public void update() {
        EntitiesManager.getInstance().players.forEach(
                Entity::update
        );
    }

    public  void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        EntitiesManager.getInstance().players.forEach(
                entity -> entity.render(gc)
        );
    }
}
