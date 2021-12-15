package nl.remcoder.adventofcode;

import java.util.*;
import java.util.stream.Stream;

public class Day15 {
    public int handlePart1(Stream<String> input) {
        int[][] grid = input.map(s -> s.chars()
                                       .map(i -> Character.digit(i, 10))
                                       .toArray())
                            .toArray(int[][]::new);

        Map<Point, Node> map = new HashMap<>();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                Point point = new Point(x, y);
                Node node = new Node(point);
                map.put(point, node);
            }
        }

        for (Point point : map.keySet()) {
            Node current = map.get(point);
            Node above = map.get(point.getAbove());
            Node below = map.get(point.getBelow());
            Node right = map.get(point.getRight());
            Node left = map.get(point.getLeft());

            addNeighbors(grid, current, above);
            addNeighbors(grid, current, below);
            addNeighbors(grid, current, right);
            addNeighbors(grid, current, left);
        }

        Node start = map.get(new Point(0, 0));
        start.setDistance(0);
        Node end = map.get(new Point(grid[0].length - 1, grid.length - 1));

        return findShortestDistance(start, end);
    }

    public int handlePart2(Stream<String> input) {
        int[][] grid = input.map(s -> s.chars()
                                       .map(i -> Character.digit(i, 10))
                                       .toArray())
                            .toArray(int[][]::new);

        int[][] newgrid = new int[grid.length * 5][grid[0].length * 5];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                newgrid[y][x] = grid[y][x];
                newgrid[y + grid.length][x] = getValue(grid[y][x], 1);
                newgrid[y + 2 * grid.length][x] = getValue(grid[y][x], 2);
                newgrid[y + 3 * grid.length][x] = getValue(grid[y][x], 3);
                newgrid[y + 4 * grid.length][x] = getValue(grid[y][x], 4);

                newgrid[y][x + grid[y].length] = getValue(grid[y][x], 1);
                newgrid[y + grid.length][x + grid[y].length] = getValue(grid[y][x], 2);
                newgrid[y + 2 * grid.length][x + grid[y].length] = getValue(grid[y][x], 3);
                newgrid[y + 3 * grid.length][x + grid[y].length] = getValue(grid[y][x], 4);
                newgrid[y + 4 * grid.length][x + grid[y].length] = getValue(grid[y][x], 5);

                newgrid[y][x + (2 * grid[y].length)] = getValue(grid[y][x], 2);
                newgrid[y + grid.length][x + (2 * grid[y].length)] = getValue(grid[y][x], 3);
                newgrid[y + 2 * grid.length][x + (2 * grid[y].length)] = getValue(grid[y][x], 4);
                newgrid[y + 3 * grid.length][x + (2 * grid[y].length)] = getValue(grid[y][x], 5);
                newgrid[y + 4 * grid.length][x + (2 * grid[y].length)] = getValue(grid[y][x], 6);

                newgrid[y][x + (3 * grid[y].length)] = getValue(grid[y][x], 3);
                newgrid[y + grid.length][x + grid[y].length * 3] = getValue(grid[y][x], 4);
                newgrid[y + 2 * grid.length][x + (3 * grid[y].length)] = getValue(grid[y][x], 5);
                newgrid[y + 3 * grid.length][x + (3 * grid[y].length)] = getValue(grid[y][x], 6);
                newgrid[y + 4 * grid.length][x + (3 * grid[y].length)] = getValue(grid[y][x], 7);

                newgrid[y][x + (4 * grid[y].length)] = getValue(grid[y][x], 4);
                newgrid[y + grid.length][x + (4 * grid[y].length)] = getValue(grid[y][x], 5);
                newgrid[y + 2 * grid.length][x + (4 * grid[y].length)] = getValue(grid[y][x], 6);
                newgrid[y + 3 * grid.length][x + (4 * grid[y].length)] = getValue(grid[y][x], 7);
                newgrid[y + 4 * grid.length][x + (4 * grid[y].length)] = getValue(grid[y][x], 8);
            }
        }

        Map<Point, Node> map = new HashMap<>();

        for (int y = 0; y < newgrid.length; y++) {
            for (int x = 0; x < newgrid[y].length; x++) {
                Point point = new Point(x, y);
                Node node = new Node(point);
                map.put(point, node);
            }
        }

        for (Point point : map.keySet()) {
            Node current = map.get(point);
            Node above = map.get(point.getAbove());
            Node below = map.get(point.getBelow());
            Node right = map.get(point.getRight());
            Node left = map.get(point.getLeft());

            addNeighbors(newgrid, current, above);
            addNeighbors(newgrid, current, below);
            addNeighbors(newgrid, current, right);
            addNeighbors(newgrid, current, left);
        }

        Node start = map.get(new Point(0, 0));
        start.setDistance(0);
        Node end = map.get(new Point(newgrid[0].length - 1, newgrid.length - 1));

        return findShortestDistance(start, end);
    }

    private int getValue(int value, int increment) {
        int newValue = (value + increment) % 9;

        if (newValue == 0) {
            return 9;
        }
        return newValue;
    }

    public int findShortestDistance(Node from, Node to) {
        Queue<Node> toVisit = new PriorityQueue<>();
        toVisit.add(from);

        while (!toVisit.isEmpty()) {
            Node min = toVisit.remove();
            if (min == to) {
                return min.getDistance();
            }
            if (min.isVisited()) {
                continue;
            }
            min.setVisited(true);
            for (Map.Entry<Node, Integer> neighborEntry : min.getAdjacentNodes().entrySet()) {
                int adjacentDistance = min.getDistance() + neighborEntry.getValue();
                Node neighbor = neighborEntry.getKey();
                if (neighbor.getDistance() > adjacentDistance && !neighbor.isVisited()) {
                    neighbor.setDistance(adjacentDistance);
                    toVisit.add(neighbor);
                }
            }
        }

        throw new RuntimeException("'to' node unreachable");
    }

    private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private void addNeighbors(int[][] grid, Node current, Node other) {
        if (other != null) {
            current.addDestination(other, grid[other.position.y][other.position.x]);
            other.addDestination(current, grid[current.position.y][current.position.x]);
        }
    }

    private void printGrid(int[][] grid) {
        for (int[] line : grid) {
            for (int pixel : line) {
                System.out.print(pixel);
            }
            System.out.println();
        }
        System.out.println();
    }

    private record Point(int x, int y) {
        public Point getAbove() {
            return new Point(x, y - 1);
        }

        public Point getBelow() {
            return new Point(x, y + 1);
        }

        public Point getRight() {
            return new Point(x + 1, y);
        }

        public Point getLeft() {
            return new Point(x - 1, y);
        }
    }

    private static class Node implements Comparable<Node> {
        private final Point position;
        private List<Node> shortestPath = new LinkedList<>();
        private int distance = Integer.MAX_VALUE;
        private Map<Node, Integer> adjacentNodes = new HashMap<>();
        private boolean visited;

        public Node(Point position) {
            this.position = position;
        }

        public void addDestination(Node destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

        public Point getPosition() {
            return position;
        }

        public List<Node> getShortestPath() {
            return shortestPath;
        }

        public void setShortestPath(List<Node> shortestPath) {
            this.shortestPath = shortestPath;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public Map<Node, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
            this.adjacentNodes = adjacentNodes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return Objects.equals(position, node.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position);
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.distance, o.distance);
        }
    }
}
