package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.model.Pair;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Stream;

public class Day20 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = new Grid<>(input.map(s -> s.chars()
                                              .mapToObj(c -> (char) c)
                                              .toArray(Character[]::new))
                                   .toArray(Character[][]::new));

        var gates = findGates(grid, findHoleCorners(grid));

        var startPosition = findMazeEntryPoint(grid, 'A');
        var exitPosition = findMazeEntryPoint(grid, 'Z');

        var start = new Step(grid, new HashMap<>(), startPosition, gates, List.of());
        start.setDistance(0);

        return Dijkstra.findShortestDistance(start, node -> ((Step) node).currentPosition.equals(exitPosition))
                       .orElseThrow(() -> new AssertionError("Eek!"))
                       .getDistance();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = new Grid<>(input.map(s -> s.chars()
                                              .mapToObj(c -> (char) c)
                                              .toArray(Character[]::new))
                                   .toArray(Character[][]::new));

        var holeCorners = findHoleCorners(grid);

        var gates = findGates(grid, holeCorners);

        var startPosition = findMazeEntryPoint(grid, 'A');
        var exitPosition = findMazeEntryPoint(grid, 'Z');

        var start = new StepWithLevels(grid, new HashMap<>(), startPosition, gates, List.of(), 0);
        start.setDistance(0);

        return Dijkstra.findShortestDistance(start, node -> {
                           StepWithLevels step = (StepWithLevels) node;
                           return step.currentPosition.equals(exitPosition) && step.level == 0;
                       })
                       .orElseThrow(() -> new AssertionError("Eek!"))
                       .getDistance();
    }

    private Pair<Coordinate, Coordinate> findHoleCorners(Grid<Character> grid) {
        Coordinate topLeft = null;
        Coordinate bottomRight = null;
        outer:
        for (var y = grid.getStarty() + 2; y <= grid.getEndy() - 2; y++) {
            for (var x = grid.getStartx() + 2; x <= grid.getEndx() - 2; x++) {
                if (grid.get(x, y) == ' ') {
                    topLeft = new Coordinate(x - 1, y - 1);
                    break outer;
                }
            }
        }
        outer:
        for (var y = grid.getEndy() - 2; y >= grid.getStarty() + 2; y--) {
            for (var x = grid.getEndx() - 2; x >= grid.getStartx() + 2; x--) {
                if (grid.get(x, y) == ' ') {
                    bottomRight = new Coordinate(x + 1, y + 1);
                    break outer;
                }
            }
        }
        return new Pair<>(topLeft, bottomRight);
    }

    private Coordinate findMazeEntryPoint(Grid<Character> grid, char entryPointId) {
        for (var y = grid.getStarty(); y <= grid.getEndy(); y++) {
            for (var x = grid.getStartx(); x <= grid.getEndx(); x++) {
                var value = grid.get(x, y);
                if (value == entryPointId) {
                    var coordinate = new Coordinate(x, y);
                    var nextAlphabeticCoordinate = findNextAlphabeticCoordinate(coordinate, grid);
                    if (grid.get(nextAlphabeticCoordinate) == entryPointId) {
                        for (var possibleEndpoint : coordinate.getStraightNeighbours()) {
                            var character = grid.get(possibleEndpoint);
                            if (character != null && character == '.') {
                                return possibleEndpoint;
                            }
                        }
                        assert nextAlphabeticCoordinate != null;
                        for (var possibleEndpoint : nextAlphabeticCoordinate.getStraightNeighbours()) {
                            var character = grid.get(possibleEndpoint);
                            if (character != null && character == '.') {
                                return possibleEndpoint;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private Map<Set<Character>, Pair<Gate, Gate>> findGates(Grid<Character> grid,
                                                            Pair<Coordinate, Coordinate> holeCorners) {
        var mappedGates = new HashMap<Set<Character>, Pair<Gate, Gate>>();
        var unmappedGates = new HashMap<Set<Character>, Gate>();

        for (var y = grid.getStarty(); y <= grid.getEndy(); y++) {
            for (var x = grid.getStartx(); x <= grid.getEndx(); x++) {
                var value = grid.get(x, y);
                if (Character.isAlphabetic(value)) {
                    var coordinate = new Coordinate(x, y);
                    var nextAlphabeticCoordinate = findNextAlphabeticCoordinate(coordinate, grid);
                    var other = grid.get(nextAlphabeticCoordinate);
                    Set<Character> gate;
                    if (value == other) {
                        if (value == 'A' || value == 'Z') {
                            continue;
                        } else {
                            gate = Set.of(value);
                        }
                    } else {
                        gate = Set.of(value, other);
                    }
                    Coordinate endPoint = null;
                    for (var possibleEndpoint : coordinate.getStraightNeighbours()) {
                        var character = grid.get(possibleEndpoint);
                        if (character != null && character == '.') {
                            endPoint = possibleEndpoint;
                            break;
                        }
                    }
                    if (endPoint == null) {
                        assert nextAlphabeticCoordinate != null;
                        for (var possibleEndpoint : nextAlphabeticCoordinate.getStraightNeighbours()) {
                            var character = grid.get(possibleEndpoint);
                            if (character != null && character == '.') {
                                endPoint = possibleEndpoint;
                                break;
                            }
                        }
                    }
                    if (unmappedGates.containsKey(gate)) {
                        if (!unmappedGates.get(gate).coordinate().equals(endPoint)) {
                            assert endPoint != null;
                            mappedGates.put(gate,
                                            new Pair<>(new Gate(endPoint, determineGateType(endPoint, holeCorners)),
                                                       unmappedGates.remove(gate)));
                        }
                    } else {
                        assert endPoint != null;
                        unmappedGates.put(gate, new Gate(endPoint, determineGateType(endPoint, holeCorners)));
                    }
                }
            }
        }

        return mappedGates;
    }

    private Type determineGateType(Coordinate endPoint, Pair<Coordinate, Coordinate> holeCorners) {
        var topLeft = holeCorners.left();
        var bottomRight = holeCorners.right();

        if (endPoint.x() >= topLeft.x() && endPoint.x() <= bottomRight.x() &&
            endPoint.y() >= topLeft.y() && endPoint.y() <= bottomRight.y()) {
            return Type.INNER;
        } else {
            return Type.OUTER;
        }
    }

    private static class Step extends Node<Step> {
        private final Grid<Character> grid;
        private final Map<Coordinate, Step> visitedCoordinates;
        private final Coordinate currentPosition;
        private final Map<Set<Character>, Pair<Gate, Gate>> gates;
        private final List<Coordinate> steps;

        private Step(Grid<Character> grid, Map<Coordinate, Step> visitedCoordinates, Coordinate currentPosition,
                     Map<Set<Character>, Pair<Gate, Gate>> gates, List<Coordinate> steps) {
            this.grid = grid;
            this.visitedCoordinates = visitedCoordinates;
            this.currentPosition = currentPosition;
            this.gates = gates;
            this.steps = steps;
        }

        @Override
        public Map<Step, Long> getNeighbors() {
            var neighbors = new HashMap<Step, Long>();

            for (var nextPosition : currentPosition.getStraightNeighbours()) {
                var value = grid.get(nextPosition);
                if (value == null) {
                    continue;
                }
                if (visitedCoordinates.containsKey(nextPosition)) {
                    neighbors.put(visitedCoordinates.get(nextPosition), 1L);
                    continue;
                }
                if (value == '.') {
                    var newSteps = new ArrayList<>(steps);
                    newSteps.add(nextPosition);
                    var nextStep = new Step(grid, visitedCoordinates, nextPosition, gates, newSteps);
                    neighbors.put(nextStep, 1L);
                    visitedCoordinates.put(nextPosition, nextStep);
                } else if (Character.isAlphabetic(value)) {
                    var other = grid.get(findNextAlphabeticCoordinate(nextPosition, grid));
                    if (value == other) {
                        continue;
                    }
                    var gate = Set.of(value, other);
                    var points = gates.get(gate);
                    Step nextStep;
                    if (points.left().coordinate().equals(currentPosition)) {
                        var newSteps = new ArrayList<>(steps);
                        newSteps.add(points.right().coordinate());
                        nextStep = new Step(grid, visitedCoordinates, points.right().coordinate(), gates, newSteps);
                    } else {
                        var newSteps = new ArrayList<>(steps);
                        newSteps.add(points.left().coordinate());
                        nextStep = new Step(grid, visitedCoordinates, points.left().coordinate(), gates, newSteps);
                    }
                    neighbors.put(nextStep, 1L);
                    visitedCoordinates.put(nextPosition, nextStep);
                }
            }

            return neighbors;
        }

        @Override
        public void printStateInformation() {
            System.out.println(steps);
        }
    }


    private static class StepWithLevels extends Node<StepWithLevels> {
        private final Grid<Character> grid;
        private final Map<Pair<Coordinate, Integer>, StepWithLevels> visitedCoordinates;
        private final Coordinate currentPosition;
        private final Map<Set<Character>, Pair<Gate, Gate>> gates;
        private final List<Pair<Coordinate, Integer>> steps;
        private final int level;

        private StepWithLevels(Grid<Character> grid, Map<Pair<Coordinate, Integer>, StepWithLevels> visitedCoordinates,
                               Coordinate currentPosition,
                               Map<Set<Character>, Pair<Gate, Gate>> gates, List<Pair<Coordinate, Integer>> steps,
                               int level) {
            this.grid = grid;
            this.visitedCoordinates = visitedCoordinates;
            this.currentPosition = currentPosition;
            this.gates = gates;
            this.steps = steps;
            this.level = level;
        }

        @Override
        public Map<StepWithLevels, Long> getNeighbors() {
            var neighbors = new HashMap<StepWithLevels, Long>();

            for (var nextPosition : currentPosition.getStraightNeighbours()) {
                var value = grid.get(nextPosition);
                if (value == null) {
                    continue;
                }
                if (visitedCoordinates.containsKey(new Pair<>(nextPosition, level))) {
                    neighbors.put(visitedCoordinates.get(new Pair<>(nextPosition, level)), 1L);
                    continue;
                }
                if (value == '.') {
                    var newSteps = new ArrayList<>(steps);
                    newSteps.add(new Pair<>(nextPosition, level));
                    var nextStep = new StepWithLevels(grid, visitedCoordinates, nextPosition, gates, newSteps, level);
                    neighbors.put(nextStep, 1L);
                    visitedCoordinates.put(new Pair<>(nextPosition, level), nextStep);
                } else if (Character.isAlphabetic(value)) {
                    var other = grid.get(findNextAlphabeticCoordinate(nextPosition, grid));
                    Set<Character> gateId;
                    if (value == other) {
                        if (value == 'A' || value == 'Z') {
                            continue;
                        } else {
                            gateId = Set.of(value);
                        }
                    } else {
                        gateId = Set.of(value, other);
                    }
                    var points = gates.get(gateId);
                    Gate gate;
                    if (points.left().coordinate().equals(currentPosition)) {
                        gate = points.right();
                    } else {
                        gate = points.left();
                    }
                    if (gate.type() == Type.OUTER) {
                        if (visitedCoordinates.containsKey(new Pair<>(gate.coordinate, level + 1))) {
                            neighbors.put(visitedCoordinates.get(new Pair<>(gate.coordinate(), level + 1)), 1L);
                        } else {
                            var newSteps = new ArrayList<>(steps);
                            newSteps.add(new Pair<>(gate.coordinate, level + 1));
                            var newStep =
                                    new StepWithLevels(grid, visitedCoordinates, gate.coordinate(), gates, newSteps,
                                                       level + 1);
                            neighbors.put(newStep, 1L);
                            visitedCoordinates.put(new Pair<>(gate.coordinate(), level + 1), newStep);
                        }
                    } else {
                        if (level > 0) {
                            if (visitedCoordinates.containsKey(new Pair<>(gate.coordinate, level - 1))) {
                                neighbors.put(visitedCoordinates.get(new Pair<>(gate.coordinate(), level - 1)), 1L);
                            } else {
                                var newSteps = new ArrayList<>(steps);
                                newSteps.add(new Pair<>(gate.coordinate, level - 1));
                                var newStep =
                                        new StepWithLevels(grid, visitedCoordinates, gate.coordinate(), gates, newSteps,
                                                           level - 1);
                                neighbors.put(newStep, 1L);
                                visitedCoordinates.put(new Pair<>(gate.coordinate(), level - 1), newStep);
                            }
                        }
                    }
                }
            }

            return neighbors;
        }

        @Override
        public void printStateInformation() {
            steps.forEach(System.out::println);
        }

        @Override
        public int compareTo(StepWithLevels o) {
            return Comparator.comparing(StepWithLevels::getLevel).thenComparing(Node::getDistance).compare(this, o);
        }

        public int getLevel() {
            return level;
        }
    }

    private static Coordinate findNextAlphabeticCoordinate(Coordinate alphabeticCoordinate, Grid<Character> grid) {
        for (var next : alphabeticCoordinate.getStraightNeighbours()) {
            var value = grid.get(next);
            if (value != null && Character.isAlphabetic(value)) {
                return next;
            }
        }
        return null;
    }

    private record Gate(Coordinate coordinate, Type type) {
    }

    private enum Type {
        INNER,
        OUTER
    }
}
