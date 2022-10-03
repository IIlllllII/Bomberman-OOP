package uet.oop.bomberman.components.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.Entity;

public class Flame extends Entity {
    private int direct;// Hướng của lửa: 0 Up, 1 Down, 2 Left, 3 Right, 4 Center
    private boolean last; // Kiểm tra kết đuôi ngọn lửa

    public Flame(int x, int y, int direct, boolean last) {
        super(x, y);
        this.direct = direct;
        this.last = last;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }
}
