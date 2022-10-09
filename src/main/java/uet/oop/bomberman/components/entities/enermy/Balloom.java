package uet.oop.bomberman.components.entities.enermy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Balloom extends Entity {
    private static List<Image> balloom;


    public static void init() {
        try {
            balloom = new ArrayList<>();
            balloom.add(new Image(Balloom.class.getResource("/sprites/balloom/mob1.png").toURI().toString()));
            balloom.add(new Image(Balloom.class.getResource("/sprites/balloom/mob2.png").toURI().toString()));
            balloom.add(new Image(Balloom.class.getResource("/sprites/balloom/mob3.png").toURI().toString()));
            balloom.add(new Image(Balloom.class.getResource("/sprites/balloom/mob4.png").toURI().toString()));
            balloom.add(new Image(Balloom.class.getResource("/sprites/balloom/mob5.png").toURI().toString()));
            balloom.add(new Image(Balloom.class.getResource("/sprites/balloom/mob6.png").toURI().toString()));
            balloom.add(new Image(Balloom.class.getResource("/sprites/balloom/mob7.png").toURI().toString()));
            balloom.add(new Image(Balloom.class.getResource("/sprites/balloom/mob8.png").toURI().toString()));
            balloom.add(new Image(Balloom.class.getResource("/sprites/balloom/mob9.png").toURI().toString()));
            balloom.add(new Image(Balloom.class.getResource("/sprites/balloom/mob10.png").toURI().toString()));
        } catch (URISyntaxException e) {
            System.out.println("balloom inti");
        }
    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public void update() {

    }
}
