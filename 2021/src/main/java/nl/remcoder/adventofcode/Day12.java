package nl.remcoder.adventofcode;

import java.util.*;
import java.util.stream.Stream;

public class Day12 {
    public int handlePart1(Stream<String> input) {
        Map<String, Cave> caves = new HashMap<>();

        input.map(s -> s.split("-"))
             .forEach(strings -> {
                 Cave cave1 = caves.computeIfAbsent(strings[0], Cave::new);
                 Cave cave2 = caves.computeIfAbsent(strings[1], Cave::new);

                 cave1.connections.add(cave2);
                 cave2.connections.add(cave1);
             });

        Cave start = caves.get("start");

        return findNumberOfRoutes(start, List.of(start));
    }

    public int handlePart2(Stream<String> input) {
        Map<String, Cave> caves = new HashMap<>();

        input.map(s -> s.split("-"))
             .forEach(strings -> {
                 Cave cave1 = caves.computeIfAbsent(strings[0], Cave::new);
                 Cave cave2 = caves.computeIfAbsent(strings[1], Cave::new);

                 cave1.connections.add(cave2);
                 cave2.connections.add(cave1);
             });

        Cave start = caves.get("start");

        start.connections.forEach(cave -> cave.connections.removeIf(cave1 -> "start".equals(cave1.label)));

        return findNumberOfRoutes(start, List.of(start), false);
    }

    private int findNumberOfRoutes(Cave cave, List<Cave> currentRoute) {
        List<Cave> route = List.copyOf(currentRoute);

        int numberOfRoutes = 0;

        if ("end".equals(cave.label)) {
            return 1;
        } else {
            for (Cave otherCave : cave.connections) {
                if (otherCave.isLarge) {
                    List<Cave> newRoute = new ArrayList<>(route);
                    newRoute.add(otherCave);
                    numberOfRoutes += findNumberOfRoutes(otherCave, newRoute);
                } else {
                    if (!currentRoute.contains(otherCave)) {
                        List<Cave> newRoute = new ArrayList<>(route);
                        newRoute.add(otherCave);
                        numberOfRoutes += findNumberOfRoutes(otherCave, newRoute);
                    }
                }
            }
        }

        return numberOfRoutes;
    }

    private int findNumberOfRoutes(Cave cave, List<Cave> currentRoute, boolean smallVisitedTwice) {
        List<Cave> route = List.copyOf(currentRoute);

        int numberOfRoutes = 0;

        if ("end".equals(cave.label)) {
            return 1;
        } else {
            for (Cave otherCave : cave.connections) {
                if (otherCave.isLarge) {
                    List<Cave> newRoute = new ArrayList<>(route);
                    newRoute.add(otherCave);
                    numberOfRoutes += findNumberOfRoutes(otherCave, newRoute, smallVisitedTwice);
                } else {
                    if (!currentRoute.contains(otherCave)) {
                        List<Cave> newRoute = new ArrayList<>(route);
                        newRoute.add(otherCave);
                        numberOfRoutes += findNumberOfRoutes(otherCave, newRoute, smallVisitedTwice);
                    } else {
                        if (!smallVisitedTwice) {
                            List<Cave> newRoute = new ArrayList<>(route);
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
