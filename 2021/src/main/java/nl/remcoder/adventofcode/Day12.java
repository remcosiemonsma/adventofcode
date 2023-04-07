package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.stream.Stream;

public class Day12 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var caves = new HashMap<String, Cave>();

        input.map(s -> s.split("-"))
             .forEach(strings -> {
                 var cave1 = caves.computeIfAbsent(strings[0], Cave::new);
                 var cave2 = caves.computeIfAbsent(strings[1], Cave::new);

                 cave1.connections.add(cave2);
                 cave2.connections.add(cave1);
             });

        var start = caves.get("start");

        return findNumberOfRoutes(start, List.of(start));
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var caves = new HashMap<String, Cave>();

        input.map(s -> s.split("-"))
             .forEach(strings -> {
                 var cave1 = caves.computeIfAbsent(strings[0], Cave::new);
                 var cave2 = caves.computeIfAbsent(strings[1], Cave::new);

                 cave1.connections.add(cave2);
                 cave2.connections.add(cave1);
             });

        var start = caves.get("start");

        start.connections.forEach(cave -> cave.connections.removeIf(cave1 -> "start".equals(cave1.label)));

        return findNumberOfRoutes(start, List.of(start), false);
    }

    private int findNumberOfRoutes(Cave cave, List<Cave> currentRoute) {
        var route = List.copyOf(currentRoute);

        var numberOfRoutes = 0;

        if ("end".equals(cave.label)) {
            return 1;
        } else {
            for (var otherCave : cave.connections) {
                if (otherCave.isLarge) {
                    var newRoute = new ArrayList<>(route);
                    newRoute.add(otherCave);
                    numberOfRoutes += findNumberOfRoutes(otherCave, newRoute);
                } else {
                    if (!currentRoute.contains(otherCave)) {
                        var newRoute = new ArrayList<>(route);
                        newRoute.add(otherCave);
                        numberOfRoutes += findNumberOfRoutes(otherCave, newRoute);
                    }
                }
            }
        }

        return numberOfRoutes;
    }

    private int findNumberOfRoutes(Cave cave, List<Cave> currentRoute, boolean smallVisitedTwice) {
        var route = List.copyOf(currentRoute);

        var numberOfRoutes = 0;

        if ("end".equals(cave.label)) {
            return 1;
        } else {
            for (var otherCave : cave.connections) {
                if (otherCave.isLarge) {
                    var newRoute = new ArrayList<>(route);
                    newRoute.add(otherCave);
                    numberOfRoutes += findNumberOfRoutes(otherCave, newRoute, smallVisitedTwice);
                } else {
                    if (!currentRoute.contains(otherCave)) {
                        var newRoute = new ArrayList<>(route);
                        newRoute.add(otherCave);
                        numberOfRoutes += findNumberOfRoutes(otherCave, newRoute, smallVisitedTwice);
                    } else {
                        if (!smallVisitedTwice) {
                            var newRoute = new ArrayList<>(route);
                            newRoute.add(otherCave);
                            numberOfRoutes += findNumberOfRoutes(otherCave, newRoute, true);
                        }
                    }
                }
            }
        }

        return numberOfRoutes;
    }

    private static class Cave {
        private final String label;
        private final boolean isLarge;
        private final Set<Cave> connections;

        private Cave(String label) {
            this.label = label;
            this.isLarge = Character.isUpperCase(label.charAt(0));
            connections = new HashSet<>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Cave cave = (Cave) o;
            return Objects.equals(label, cave.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }
    }
}
