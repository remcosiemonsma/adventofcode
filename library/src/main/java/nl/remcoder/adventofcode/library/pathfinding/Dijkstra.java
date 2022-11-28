package nl.remcoder.adventofcode.library.pathfinding;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Predicate;

public class Dijkstra {
    public static long findShortestDistance(Node from, Predicate<Node> isNodeEndstate) {
        Queue<Node> toVisit = new PriorityQueue<>();
        toVisit.add(from);

        while (!toVisit.isEmpty()) {
            Node min = toVisit.remove();
            if (isNodeEndstate.test(min)) {
                min.printStateInformation();
                return min.getDistance();
            }
            if (min.isVisited()) {
                continue;
            }
            min.setVisited(true);
            for (Map.Entry<? extends Node, Long> neighborEntry : min.getNeighbors().entrySet()) {
                long adjacentDistance = min.getDistance() + neighborEntry.getValue();
                Node neighbor = neighborEntry.getKey();
                if (neighbor.getDistance() > adjacentDistance && !neighbor.isVisited()) {
                    neighbor.setDistance(adjacentDistance);
                    toVisit.add(neighbor);
                }
            }
        }

        throw new RuntimeException("Target state unreachable");
    }
}
