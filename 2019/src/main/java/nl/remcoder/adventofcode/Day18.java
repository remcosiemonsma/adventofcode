package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var keys = new HashSet<Character>();

        keys.add('@');

        var grid = new Grid<>(input.map(string -> string.chars()
                                                        .mapToObj(c -> (char) c)
                                                        .peek(c -> {
                                                            if (Character.isAlphabetic(c) && Character.isLowerCase(c)) {
                                                                keys.add(c);
                                                            }
                                                        })
                                                        .toArray(Character[]::new))
                                   .toArray(Character[][]::new));

        var startPosition = grid.findValue('@').orElseThrow(() -> new AssertionError("Eek!"));

        var keyPositions = keys.stream()
                               .collect(Collectors.toMap(Function.identity(),
                                                         key -> grid.findValue(key)
                                                                    .orElseThrow(() -> new AssertionError("Eek!"))));

        var positionKeys = keyPositions.entrySet()
                                       .stream()
                                       .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        var pathsForKeys = keys.stream()
                               .collect(Collectors.toMap(Function.identity(),
                                                         key -> keys.stream()
                                                                    .filter(otherKey -> key != otherKey)
                                                                    .collect(Collectors.toMap(Function.identity(),
                                                                                              otherKey -> createPathTo(
                                                                                                      keyPositions.get(
                                                                                                              key),
                                                                                                      keyPositions.get(
                                                                                                              otherKey),
                                                                                                      grid)))
                                                        ));

        keys.remove('@');

        return findShortestPath(startPosition, Set.of('@'), keys, keyPositions, positionKeys, pathsForKeys,
                                new HashMap<>());
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var keys = new HashSet<Character>();

        var grid = new Grid<>(input.map(string -> string.chars()
                                                        .mapToObj(c -> (char) c)
                                                        .peek(c -> {
                                                            if (Character.isAlphabetic(c) && Character.isLowerCase(c)) {
                                                                keys.add(c);
                                                            }
                                                        })
                                                        .toArray(Character[]::new))
                                   .toArray(Character[][]::new));

        createDoorsOnGrid(grid);

        var startPositions = grid.findValues('@');

        Map<Coordinate, Set<Character>> reachableKeysForPositions = startPositions.stream()
                                                                                  .collect(Collectors.toMap(
                                                                                          Function.identity(),
                                                                                          startPosition -> findReachableKeysForRobot(
                                                                                                  startPosition,
                                                                                                  grid)));

        var keyPositions = keys.stream()
                               .collect(Collectors.toMap(Function.identity(),
                                                         key -> grid.findValue(key)
                                                                    .orElseThrow(() -> new AssertionError("Eek!"))));

        var positionKeys = keyPositions.entrySet()
                                       .stream()
                                       .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        var pathsForKeysForRobot = reachableKeysForPositions
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().stream()
                                              .collect(Collectors.toMap(Function.identity(),
                                                                        key -> keys.stream()
                                                                                   .filter(otherKey -> key != otherKey)
                                                                                   .collect(Collectors.toMap(
                                                                                           Function.identity(),
                                                                                           otherKey -> {
                                                                                               Coordinate
                                                                                                       startPosition =
                                                                                                       keyPositions.get(
                                                                                                               key);
                                                                                               Coordinate
                                                                                                       targetPosition =
                                                                                                       keyPositions.get(
                                                                                                               otherKey);
                                                                                               if (startPosition ==
                                                                                                   null) {
                                                                                                   startPosition =
                                                                                                           entry.getKey();
                                                                                               }
                                                                                               if (targetPosition ==
                                                                                                   null) {
                                                                                                   targetPosition =
                                                                                                           entry.getKey();
                                                                                               }
                                                                                               return createPathTo(
                                                                                                       startPosition,
                                                                                                       targetPosition,
                                                                                                       grid);
                                                                                           }))
                                                                       ))
                                        ));

        reachableKeysForPositions.forEach((coordinate, characters) -> characters.remove('@'));

        return startPositions.stream()
                             .mapToInt(startPosition ->
                                               findShortestPath(startPosition,
                                                                reachableKeysForPositions.entrySet()
                                                                                         .stream()
                                                                                         .filter(entry -> !entry.getKey()
                                                                                                                .equals(startPosition))
                                                                                         .flatMap(
                                                                                                 entry -> entry.getValue()
                                                                                                               .stream())
                                                                                         .collect(Collectors.toSet()),
                                                                reachableKeysForPositions.get(startPosition),
                                                                keyPositions,
                                                                positionKeys,
                                                                pathsForKeysForRobot.get(startPosition),
                                                                new HashMap<>())).sum();
    }

    private int findShortestPath(Coordinate current, Set<Character> collectedKeys,
                                 Set<Character> remainingKeys, Map<Character, Coordinate> keyPositions,
                                 Map<Coordinate, Character> positionKeys,
                                 Map<Character, Map<Character, Optional<Path>>> pathsForKeys, Map<State, Integer> memo) {
        var state = new State(current, remainingKeys);
        if (memo.containsKey(state)) {
            return memo.get(state);
        }
        if (remainingKeys.isEmpty()) {
            return 0;
        }
        var positionKey = positionKeys.get(current);
        if (positionKey == null) {
            positionKey = '@';
        }
        var shortestPath = Integer.MAX_VALUE;
        for (var key : remainingKeys) {
            var path = pathsForKeys.get(positionKey).get(key).orElseThrow(() -> new AssertionError("Eek!"));

            if (collectedKeys.containsAll(path.doors())) {
                var newCollectedKeys = new HashSet<>(collectedKeys);
                newCollectedKeys.add(key);
                var newRemainingKeys = new HashSet<>(remainingKeys);
                newRemainingKeys.remove(key);
                var distance = path.distance() +
                               findShortestPath(keyPositions.get(key), newCollectedKeys, newRemainingKeys,
                                                keyPositions, positionKeys, pathsForKeys, memo);
                if (distance < shortestPath) {
                    shortestPath = distance;
                }
            }
        }
        memo.put(state, shortestPath);
        return shortestPath;
    }

    private void createDoorsOnGrid(Grid<Character> grid) {
        var startPosition = grid.findValue('@').orElseThrow(() -> new AssertionError("Eek!"));

        grid.set(startPosition, '#');
        grid.set(startPosition.topLeft(), '@');
        grid.set(startPosition.topRight(), '@');
        grid.set(startPosition.bottomLeft(), '@');
        grid.set(startPosition.bottomRight(), '@');
        grid.set(startPosition.above(), '#');
        grid.set(startPosition.below(), '#');
        grid.set(startPosition.left(), '#');
        grid.set(startPosition.right(), '#');
    }

    private Set<Character> findReachableKeysForRobot(Coordinate startPosition, Grid<Character> grid) {
        var keys = new HashSet<Character>();
        keys.add('@');
        var positionsToInvestigate = Set.of(startPosition);
        var visitedPositions = new HashSet<Coordinate>();

        while (!positionsToInvestigate.isEmpty()) {
            var newPositionsToInvestigate = new HashSet<Coordinate>();
            for (var position : positionsToInvestigate) {
                for (var nextPosition : position.getStraightNeighbours()) {
                    if (visitedPositions.contains(nextPosition)) {
                        continue;
                    }
                    visitedPositions.add(nextPosition);
                    var value = grid.get(nextPosition);

                    if (value != null && value != '#') {
                        if (Character.isAlphabetic(value) && Character.isLowerCase(value)) {
                            keys.add(value);
                        }
                        newPositionsToInvestigate.add(nextPosition);
                    }
                }
            }
            positionsToInvestigate = newPositionsToInvestigate;
        }

        return keys;
    }

    private Optional<Path> createPathTo(Coordinate startPosition, Coordinate targetPosition, Grid<Character> grid) {
        var start = new Step(startPosition, Set.of(), grid, new HashMap<>());
        start.setDistance(0);
        return Dijkstra.findShortestDistance(start, node -> ((Step) node).currentPosition.equals(
                               targetPosition))
                       .map(node -> (Step) node)
                       .map(step -> new Path(step.foundDoors, (int) step.getDistance()));
    }

    private static class Step extends Node {
        private final Coordinate currentPosition;
        private final Set<Character> foundDoors;
        private final Grid<Character> grid;
        private final Map<Coordinate, Step> visitedPositions;

        private Step(Coordinate currentPosition, Set<Character> foundDoors, Grid<Character> grid,
                     Map<Coordinate, Step> visitedPositions) {
            this.currentPosition = currentPosition;
            this.foundDoors = foundDoors;
            this.grid = grid;
            this.visitedPositions = visitedPositions;
        }

        @Override
        public Map<? extends Node, Long> getNeighbors() {
            var neighbors = new HashMap<Step, Long>();
            for (var nextPosition : currentPosition.getStraightNeighbours()) {
                if (visitedPositions.containsKey(nextPosition)) {
                    neighbors.put(visitedPositions.get(nextPosition), 1L);
                } else {
                    var value = grid.get(nextPosition);
                    if (value != null) {
                        if (value != '#') {
                            var newDoors = new HashSet<>(foundDoors);
                            if (Character.isAlphabetic(value) && Character.isUpperCase(value)) {
                                newDoors.add(Character.toLowerCase(value));
                            }
                            var newStep = new Step(nextPosition, newDoors, grid, visitedPositions);
                            visitedPositions.put(nextPosition, newStep);
                            neighbors.put(newStep, 1L);
                        }
                    }
                }
            }
            return neighbors;
        }

        @Override
        public void printStateInformation() {

        }
    }

    private record State(Coordinate position, Set<Character> remainingKeys) {
    }

    private record Path(Set<Character> doors, int distance) {
    }
}
