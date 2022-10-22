module uet.oop.bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.media;

    opens uet.oop.bomberman to javafx.fxml;
    exports uet.oop.bomberman;
    exports uet.oop.bomberman.config;
    exports uet.oop.bomberman.core;
    exports uet.oop.bomberman.core.stages;
    exports uet.oop.bomberman.components.entities;
    exports uet.oop.bomberman.components.entities.players;
    exports uet.oop.bomberman.components.entities.enemies;
    exports uet.oop.bomberman.components.entities.enemies.normal;
    exports uet.oop.bomberman.core.scenes.game;
}