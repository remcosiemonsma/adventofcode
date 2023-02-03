package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day12 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        List<String> lines = input.toList();

        Grid<HillSide> hill = new Grid<>(0, 0, lines.get(0).length(), lines.size());

        HillSide start = null;

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                Coordinate coordinate = new Coordinate(x, y);
                char height = line.charAt(x);
                HillSide hillSide = new HillSide(height, coordinate, hill);
                hill.set(coordinate, hillSide);
                if (height == 'S') {
                    start = hillSide;
                }
            }
        }

        if (start == null) {
            throw new AssertionError("Eek!");
        }

        start.setDistance(0);
        start.setPath(List.of(start));

        return Dijkstra.findShortestDistance(start, node -> ((HillSide) node).getHeight() == 'E')
                       .orElseThrow(() -> new AssertionError("Eek!"))
                       .getDistance();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        List<String> lines = input.toList();

        Grid<HillSide> hill = new Grid<>(0, 0, lines.get(0).length(), lines.size());
        List<HillSide> allHillSides = new ArrayList<>();

        List<HillSide> possibleStarts = new ArrayList<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                Coordinate coordinate = new Coordinate(x, y);
                char height = line.charAt(x);
                HillSide hillSide = new HillSide(height, coordinate, hill);
                hill.set(coordinate, hillSide);
                allHillSides.add(hillSide);
                if (height == 'S' || height == 'a') {
                    hillSide.setPath(List.of(hillSide));
                    possibleStarts.add(hillSide);
                }
            }
        }

        return possibleStarts.stream()
                             .mapToLong(start -> mapDistanceFromPossibleStart(allHillSides, start))
                             .min()
                             .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private static long mapDistanceFromPossibleStart(List<HillSide> allHillSides, HillSide start) {
        start.setDistance(0);
        start.setPath(List.of(start));

        long shortestDistance = Long.MAX_VALUE;
        try {
            shortestDistance = Dijkstra.findShortestDistance(start, node -> ((HillSide) node).getHeight() == 'E')
                                       .orElseThrow(() -> new AssertionError("Eek!"))
                                       .getDistance();
        } catch (RuntimeException ignored) {
        }

        allHillSides.forEach(HillSide::reset);

        return shortestDistance;
    }

    private static class HillSide extends Node {
        private final char height;
        private final Coordinate coordinate;
        private final Grid<HillSide> hill;
        private List<HillSide> path;

        private HillSide(char height, Coordinate coordinate, Grid<HillSide> hill) {
            this.height = height;
            this.coordinate = coordinate;
            this.hill = hill;
        }

        @Override
        public Map<? extends Node, Long> getNeighbors() {
            Map<HillSide, Long> neighbors = new HashMap<>();

            for (Coordinate neighborCoordinate : coordinate.getStraightNeighbours()) {
                HillSide neighbor = hill.get(neighborCoordinate);

                if (isNeighborReachable(neighbor)) {
                    if (neighbor.getPath() == null) {
                        List<HillSide> newPath = new ArrayList<>(path);
                        newPath.add(neighbor);
                        neighbor.setPath(List.copyOf(newPath));
                    }
                    neighbors.put(neighbor, 1L);
                }
            }

            return neighbors;
        }

        private boolean isNeighborReachable(HillSide neighbor) {
            if (neighbor == null) {
                return false;
            }
            if (neighbor.getHeight() == 'E') {
                return height == 'z' || height == 'y';
            }
            if (height == 'S') {
                return neighbor.getHeight() == 'a' || neighbor.getHeight() == 'b';
            }
            return neighbor.getHeight() <= height + 1;
        }

        @Override
        public void printStateInformation() {
            System.out.println(path);
        }

        @Override
        public String toString() {
            return "" + height;
        }

        public char getHeight() {
            return height;
        }

        public List<HillSide> getPath() {
            return path;
        }

        public void setPath(List<HillSide> path) {
            this.path = path;
        }

        public void reset() {
            setDistance(Integer.MAX_VALUE);
            setVisited(false);
            path = null;
        }
    }
}
