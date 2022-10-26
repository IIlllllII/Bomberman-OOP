package uet.oop.bomberman.core.algo;

import uet.oop.bomberman.components.maps.LevelMap;

import java.util.*;

public class AStar {
    public static Node[][] gameMap = null;
    private final Set<Node> closedList;
    private final PriorityQueue<Node> openList;

    public AStar() {
        this.closedList = new HashSet<>();
        this.openList = new PriorityQueue<>(Comparator.comparing(Node::getF));
        if (gameMap == null) {
            initMap();
        }
    }

    public static void initMap() {
        LevelMap levelMap = LevelMap.getInstance();
        char[][] mapHash = levelMap.getMapHash();
        int row = mapHash.length;
        int col = mapHash[0].length;
        gameMap = new Node[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (mapHash[i][j] != levelMap.getHash("wall")
                    && mapHash[i][j] != levelMap.getHash("brick")) {
                    gameMap[i][j] = new Node(i, j);
                } else {
                    gameMap[i][j] = null;
                }
            }
        }
    }

    public List<Node> findPath(Node start, Node target) {
//        System.out.println("start: " + target.getRow() + " " + target.getCol());
//        System.out.println(LevelMap.getInstance().getHashAt(target.getRow(), target.getCol())
//        == LevelMap.getInstance().getHash("grass"));
//        if (start != null) {
//            return null;
//        }
        openList.add(start);
        start.setF(start.getF() + start.getH());

        while (!openList.isEmpty()) {
            Node node = openList.poll();
            closedList.add(node);

            if (node.equals(target)) {
                return getPath(node);
            }
            int row = node.getRow();
            int col = node.getCol();
            List<Node> neighbors = new ArrayList<>();
            if (gameMap[row + 1][col] != null) {
                neighbors.add(gameMap[row + 1][col]);
            }
            if (gameMap[row][col + 1] != null) {
                neighbors.add(gameMap[row][col + 1]);
            }
            if (gameMap[row - 1][col] != null) {
                neighbors.add(gameMap[row - 1][col]);
            }
            if (gameMap[row][col - 1] != null) {
                neighbors.add(gameMap[row][col - 1]);
            }

            if (neighbors.size() == 0) {
                return null;
            }

            //For every neighbor node from `neighbors`:
            for (Node neighbor : neighbors) {
                int total = node.getG() + 1;
                if (!openList.contains(neighbor)
                        && !closedList.contains(neighbor)) {
                    neighbor.setParent(node);
                    neighbor.setG(total);
                    neighbor.calcHeuristic(target);
                    neighbor.setF(neighbor.getG() + neighbor.getH());
                    openList.add(neighbor);
                } else {
                    if (total < neighbor.getG()) {
                        neighbor.setParent(node);
                        neighbor.setG(total);
                        neighbor.calcHeuristic(target);
                        neighbor.setF(neighbor.getG() + neighbor.getH());

                        if (closedList.contains(neighbor)) {
                            closedList.remove(neighbor);
                            openList.add(neighbor);
                        }
                    }
                }
            }
            closedList.add(node);
        }
        return null;
    }

    public List<Node> getPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node);
            node = node.getParent();
        }
        return path;
    }

//    public void testPrintPath() {
//        List<Node> path = findPath(
//            new Node(11, 2),
//            new Node(4, 9)
//        );
//        if (path == null) {
//            System.out.println("Path is null, can not move");
//            return;
//        }
//
//        for (Node node : path) {
//            if (node != null) {
//                System.out.print("(" + node.getRow() + "," + node.getCol() + ") -> ");
//            }
//        }
//        System.out.println();
//    }
}
