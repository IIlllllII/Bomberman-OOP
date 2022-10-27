package uet.oop.bomberman.config;

public enum Action {
    APPEAR("appear"),
    IDLE("idle"),
    MOVING("moving"),
    ATTACK("attack"),
    DEAD("dead");

    public final String label;
    Action(String label) {
        this.label = label;
    }
}
