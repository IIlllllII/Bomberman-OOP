package uet.oop.bomberman.components.entities;

public interface Killable {
    public boolean isKilled();
    public int  getLives();
    public void  setLives(int lives);
}
