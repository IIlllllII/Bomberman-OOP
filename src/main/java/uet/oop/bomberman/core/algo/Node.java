package uet.oop.bomberman.core.algo;

public class Node implements Comparable<Node> {
    private static int idCounter = 0;
    private final int id;

    private final int row;
    private final int col;

    private Node parent = null;

    //Cost function f(n)
    private int f = Integer.MAX_VALUE;
    //Move function g(n)
    private int g = Integer.MAX_VALUE;
    //Heuristic function h(n)
    private int h;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public Node getParent() {
        return parent;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void calcHeuristic(Node target) {
        //Manhattan Distance
        this.h = Math.abs(this.row - target.getRow())
                + Math.abs(this.col - target.getCol());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node)) {
            return false;
        }
        return (this.row == ((Node) o).getRow())
                && (this.col == ((Node) o).getCol());
    }

    @Override
    public int compareTo(Node o) {
        return this.f - o.f;
    }
}
