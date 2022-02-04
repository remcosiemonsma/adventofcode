package nl.remcoder.adventofcode.library.pathfinding;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Predicate;

public class Dijkstra {
    public static int findShortestDistance(Node from, Predicate<Node> isNodeEndstate) {
        Queue<Node> toVisit = new PriorityQueue<>();
        toVisit.add(from);

        while (!toVisit.isEmpty()) {
            Node min = toVisit.remove();
            if (isNodeEndstate.test(min)) {
                return min.getDistance();
            }
            if (min.isVisited()) {
                continue;
            }
            min.setVisited(true);
            for (Map.Entry<? extends Node, Integer> neighborEntry : min.getNeighbors().entrySet()) {
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
}
