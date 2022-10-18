package uet.oop.bomberman.config;

public enum CharacterStatus {
    IDLE("idle"),
    MOVING("moving"),
    DEAD("dead");

    public final String label;
    CharacterStatus(String label) {
        this.label = label;
    }
}
