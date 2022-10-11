package uet.oop.bomberman.components.entities;

public interface Killable {
    boolean isKilled();
    int  getLives();
    void  setLives(int lives);
}
