package uet.oop.bomberman.core.algo;

import uet.oop.bomberman.components.maps.LevelMap;

import java.util.*;

public class AStar {
    public AStar() {}

    public LinkedList<Node> findPath(Node start, Node target) {
        if (!isValidNode(start) || !isValidNode(target)) {
            System.out.println(isValidNode(start) + " & " + isValidNode(target));
            return null;
        }
        Set<Node> closedList = new HashSet<>();
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparing(Node::getF));
        openList.add(start);
        start.setF(start.getF() + start.getH());

        LevelMap levelMap = LevelMap.getInstance();

        while (!openList.isEmpty()) {
            Node node = openList.poll();
            closedList.add(node);

            if (node.equals(target)) {
                return getPath(node);
            }
            int row = node.getRow();
            int col = node.getCol();
            List<Node> neighbors = new ArrayList<>();

            if (levelMap.getHashAt(row + 1, col) == levelMap.getHash("grass")) {
                neighbors.add(new Node(row + 1, col));
            }

            if (levelMap.getHashAt(row, col + 1) == levelMap.getHash("grass")) {
                neighbors.add(new Node(row, col + 1));
            }

            if (levelMap.getHashAt(row - 1, col) == levelMap.getHash("grass")) {
                neighbors.add(new Node(row - 1, col));
            }

            if (levelMap.getHashAt(row, col - 1) == levelMap.getHash("grass")) {
                neighbors.add(new Node(row, col - 1));
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

    private LinkedList<Node> getPath(Node node) {
        LinkedList<Node> path = new LinkedList<>();
        while (node != null) {
            path.add(0, node);
            node = node.getParent();
        }
        return path;
    }

    private boolean isValidNode(Node node) {
        char[][] mapHash = LevelMap.getInstance().getMapHash();
        int i = node.getRow();
        int j = node.getCol();
        return i >= 0 && j >= 0 && i < mapHash.length && j < mapHash[0].length;
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
