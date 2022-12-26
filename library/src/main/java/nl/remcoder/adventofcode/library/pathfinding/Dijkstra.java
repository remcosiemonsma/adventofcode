package nl.remcoder.adventofcode.library.pathfinding;

import java.util.PriorityQueue;
import java.util.function.Predicate;

public class Dijkstra {
    public static Node findShortestDistance(Node from, Predicate<Node> isNodeEndstate) {
        var toVisit = new PriorityQueue<Node>();
        toVisit.add(from);

        while (!toVisit.isEmpty()) {
            var min = toVisit.remove();
            if (isNodeEndstate.test(min)) {
                min.printStateInformation();
                return min;
            }
            if (min.isVisited()) {
                continue;
            }
            min.setVisited(true);
            for (var neighborEntry : min.getNeighbors().entrySet()) {
                long adjacentDistance = min.getDistance() + neighborEntry.getValue();
                var neighbor = neighborEntry.getKey();
                if (neighbor.getDistance() > adjacentDistance && !neighbor.isVisited()) {
                    neighbor.setDistance(adjacentDistance);
                    toVisit.add(neighbor);
                }
            }
        }

        throw new RuntimeException("Target state unreachable");
    }
}
