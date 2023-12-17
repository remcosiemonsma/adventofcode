package nl.remcoder.adventofcode.library.pathfinding;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.Predicate;

public class Dijkstra {
    public static Optional<Node<?>> findShortestDistance(List<Node<?>> from, Predicate<Node<?>> isNodeEndstate) {
        var toVisit = new PriorityQueue<Node<?>>();
        toVisit.addAll(from);

        var iterations = 0;

        while (!toVisit.isEmpty()) {
            iterations++;
            var min = toVisit.remove();
            if (isNodeEndstate.test(min)) {
                return Optional.of(min);
            }
            if (min.isVisited()) {
                continue;
            }
            min.setVisited(true);
            for (var neighborEntry : min.getNeighbors().entrySet()) {
                long adjacentDistance = min.getDistance() + neighborEntry.getValue();
                var neighbor = neighborEntry.getKey();
                if (neighbor.getDistance() > adjacentDistance) {
                    neighbor.setDistance(adjacentDistance);
                    toVisit.add(neighbor);
                }
            }
        }

        System.out.println("Nothing found in %d iterations".formatted(iterations));

        return Optional.empty();
    }
}
