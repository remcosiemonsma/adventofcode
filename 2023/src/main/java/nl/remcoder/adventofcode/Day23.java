package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.*;
import java.util.stream.Stream;

public class Day23 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var start = findStart(grid);
        var exit = findExit(grid);

        var startCrossRoad = new CrossRoad(start);
        var exitCrossroad = new CrossRoad(exit);

        var foundCrossroads = new HashMap<Coordinate, CrossRoad>();
        foundCrossroads.put(start, startCrossRoad);
        foundCrossroads.put(exit, exitCrossroad);

        buildGraph(startCrossRoad, grid, foundCrossroads);

        return findPaths(startCrossRoad, Set.of(startCrossRoad), exitCrossroad).stream()
                                                                .max(Long::compareTo)
                                                                .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var start = findStart(grid);
        var exit = findExit(grid);

        var startCrossRoad = new CrossRoad(start);
        var exitCrossroad = new CrossRoad(exit);

        var foundCrossroads = new HashMap<Coordinate, CrossRoad>();
        foundCrossroads.put(start, startCrossRoad);
        foundCrossroads.put(exit, exitCrossroad);

        buildGraph2(startCrossRoad, grid, foundCrossroads);

        return findPaths(startCrossRoad, Set.of(startCrossRoad), exitCrossroad).stream()
                                                                .max(Long::compareTo)
                                                                .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private List<Long> findPaths(CrossRoad currentCrossroad, Set<CrossRoad> visitedCrossroads,
                                 CrossRoad exitCrossroad) {
        if (currentCrossroad == exitCrossroad) {
            return List.of(0L);
        }

        var pathLengths = new ArrayList<Long>();
        for (var crossroad : currentCrossroad.getNeighbors().keySet()) {
            if (!visitedCrossroads.contains(crossroad)) {
                var newVisitedCrossroads = new HashSet<>(visitedCrossroads);
                newVisitedCrossroads.add(crossroad);
                var nextPaths = findPaths(crossroad, newVisitedCrossroads, exitCrossroad);
                nextPaths.forEach(length -> pathLengths.add(length + currentCrossroad.getNeighbors().get(crossroad)));
            }
        }
        return pathLengths;
    }

    private void buildGraph(CrossRoad currentCrossroad, Grid<Character> grid,
                            Map<Coordinate, CrossRoad> foundCrossroads) {
        if (!currentCrossroad.getNeighbors().isEmpty()) {
            return;
        }

        var currentPoint = currentCrossroad.coordinate();

        var pointsToVisit = new HashSet<>(currentPoint.getStraightNeighbours());
        pointsToVisit.removeIf(coordinate -> !isLegalMove(grid, coordinate, currentPoint));

        for (var pointToVisit : pointsToVisit) {
            var visitedPoints = new HashSet<Coordinate>();
            visitedPoints.add(currentPoint);
            visitedPoints.add(pointToVisit);
            var nextPossibleSteps = new ArrayList<Coordinate>();
            nextPossibleSteps.add(pointToVisit);
            var steps = 0L;
            Coordinate nextPossibleStep = null;
            while (nextPossibleSteps.size() == 1) {
                steps++;
                nextPossibleStep = nextPossibleSteps.removeFirst();
                for (var neighbour : nextPossibleStep.getStraightNeighbours()) {
                    var c = grid.get(neighbour);
                    if (!visitedPoints.contains(neighbour) && c != null && c != '#') {
                        if (c == '.') {
                            nextPossibleSteps.add(neighbour);
                        } else if (c == '>') {
                            if (nextPossibleStep.getDirection(neighbour) == Direction.RIGHT) {
                                nextPossibleSteps.add(neighbour);
                            }
                        } else if (c == 'v') {
                            if (nextPossibleStep.getDirection(neighbour) == Direction.DOWN) {
                                nextPossibleSteps.add(neighbour);
                            }
                        } else if (c == '<') {
                            if (nextPossibleStep.getDirection(neighbour) == Direction.LEFT) {
                                nextPossibleSteps.add(neighbour);
                            }
                        } else if (c == '^') {
                            if (nextPossibleStep.getDirection(neighbour) == Direction.UP) {
                                nextPossibleSteps.add(neighbour);
                            }
                        }
                        visitedPoints.add(neighbour);
                    }
                }
            }
            var crossroad = foundCrossroads.computeIfAbsent(nextPossibleStep, CrossRoad::new);
            currentCrossroad.getNeighbors().put(crossroad, steps);
            if (nextPossibleSteps.size() > 1) {
                buildGraph(crossroad, grid, foundCrossroads);
            }
        }
    }

    private void buildGraph2(CrossRoad currentCrossroad, Grid<Character> grid,
                             Map<Coordinate, CrossRoad> foundCrossroads) {
        if (!currentCrossroad.getNeighbors().isEmpty()) {
            return;
        }

        var currentPoint = currentCrossroad.coordinate();

        var pointsToVisit = new HashSet<>(currentPoint.getStraightNeighbours());
        pointsToVisit.removeIf(coordinate -> grid.get(coordinate) == null || grid.get(coordinate) == '#');

        for (var pointToVisit : pointsToVisit) {
            var visitedPoints = new HashSet<Coordinate>();
            visitedPoints.add(currentPoint);
            visitedPoints.add(pointToVisit);
            var nextPossibleSteps = new ArrayList<Coordinate>();
            nextPossibleSteps.add(pointToVisit);
            var steps = 0L;
            Coordinate nextPossibleStep = null;
            while (nextPossibleSteps.size() == 1) {
                steps++;
                nextPossibleStep = nextPossibleSteps.removeFirst();
                for (var neighbour : nextPossibleStep.getStraightNeighbours()) {
                    var c = grid.get(neighbour);
                    if (!visitedPoints.contains(neighbour) && c != null && c != '#') {
                        nextPossibleSteps.add(neighbour);
                        visitedPoints.add(neighbour);
                    }
                }
            }
            var crossroad = foundCrossroads.computeIfAbsent(nextPossibleStep, CrossRoad::new);
            currentCrossroad.getNeighbors().put(crossroad, steps);
            if (nextPossibleSteps.size() > 1) {
                buildGraph2(crossroad, grid, foundCrossroads);
            }
        }
    }

    private boolean isLegalMove(Grid<Character> grid, Coordinate neighbour, Coordinate currentPoint) {
        var c = grid.get(neighbour);

        if (c == null) {
            return false;
        }
        if (c == '.') {
            return true;
        }
        if (c == '>') {
            return currentPoint.getDirection(neighbour) == Direction.RIGHT;
        }
        if (c == 'v') {
            return currentPoint.getDirection(neighbour) == Direction.DOWN;
        }
        if (c == '<') {
            return currentPoint.getDirection(neighbour) == Direction.LEFT;
        }
        if (c == '^') {
            return currentPoint.getDirection(neighbour) == Direction.UP;
        }
        return false;
    }

    private Coordinate findStart(Grid<Character> grid) {
        for (var x = 0; x <= grid.getEndx(); x++) {
            var coordinate = new Coordinate(x, 0);
            if (grid.get(coordinate) == '.') {
                return coordinate;
            }
        }
        throw new AssertionError("Eek!");
    }

    private Coordinate findExit(Grid<Character> grid) {
        for (var x = 0; x <= grid.getEndx(); x++) {
            var coordinate = new Coordinate(x, grid.getEndy());
            if (grid.get(coordinate) == '.') {
                return coordinate;
            }
        }
        throw new AssertionError("Eek!");
    }

    private static class CrossRoad {
        private final Map<CrossRoad, Long> neighbours;
        private final Coordinate coordinate;

        private CrossRoad(Coordinate coordinate) {
            this.neighbours = new HashMap<>();
            this.coordinate = coordinate;
        }

        public Coordinate coordinate() {
            return coordinate;
        }

        public Map<CrossRoad, Long> getNeighbors() {
            return neighbours;
        }
    }
}
