package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.model.Vector;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Stream;

public class Day17 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = GridFactory.createNumberedGridFromInput(input);

        var startLocation = Coordinate.ORIGIN;
        var destination = new Coordinate(grid.getEndx(), grid.getEndy());

        var startRight = new StepCrucible(startLocation,
                                          Direction.RIGHT,
                                          destination,
                                          grid,
                                          1,
                                          new ArrayList<>(),
                                          new HashMap<>());

        startRight.setDistance(0);

        var startDown = new StepCrucible(startLocation,
                                         Direction.DOWN,
                                         destination,
                                         grid,
                                         1,
                                         new ArrayList<>(),
                                         new HashMap<>());

        startDown.setDistance(0);

        return Dijkstra.findShortestDistance(List.of(startRight, startDown), node -> {
                           StepCrucible stepCrucible = (StepCrucible) node;
                           return stepCrucible.currentLocation.equals(destination);
                       })
                       .orElseThrow(() -> new AssertionError("Eek!"))
                       .getDistance();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = GridFactory.createNumberedGridFromInput(input);

        var startLocation = Coordinate.ORIGIN;
        var destination = new Coordinate(grid.getEndx(), grid.getEndy());

        var startRight = new StepUltraCrucible(startLocation,
                                               Direction.RIGHT,
                                               destination,
                                               grid,
                                               1,
                                               new ArrayList<>(),
                                               new HashMap<>());

        startRight.setDistance(0);

        var startDown = new StepUltraCrucible(startLocation,
                                              Direction.DOWN,
                                              destination,
                                              grid,
                                              1,
                                              new ArrayList<>(),
                                              new HashMap<>());

        startDown.setDistance(0);

        return Dijkstra.findShortestDistance(List.of(startRight, startDown), node -> {
                           StepUltraCrucible stepCrucible = (StepUltraCrucible) node;
                           return stepCrucible.currentLocation.equals(destination) && stepCrucible.lineLength > 3;
                       })
                       .orElseThrow(() -> new AssertionError("Eek!"))
                       .getDistance();
    }

    private static class StepCrucible extends Node<StepCrucible> {
        private final Coordinate currentLocation;
        private final Direction currentDirection;
        private final Coordinate destination;
        private final Grid<Integer> grid;
        private final int lineLength;
        private final List<Vector> directions;
        private final Map<StepKey, StepCrucible> visitedSteps;

        private StepCrucible(Coordinate currentLocation, Direction currentDirection, Coordinate destination,
                             Grid<Integer> grid, int lineLength, List<Vector> directions,
                             Map<StepKey, StepCrucible> visitedSteps) {
            this.currentLocation = currentLocation;
            this.currentDirection = currentDirection;
            this.destination = destination;
            this.grid = grid;
            this.lineLength = lineLength;
            this.directions = directions;
            this.visitedSteps = visitedSteps;
        }

        @Override
        public Map<StepCrucible, Long> getNeighbors() {
            var neighbours = new HashMap<StepCrucible, Long>();

            var left = currentDirection.rotateCounterClockWise();

            var locationLeft = currentLocation.getNeighbor(left);

            processNextLocation(locationLeft, left, neighbours, 1);

            if (lineLength < 3) {
                var locationStraight = currentLocation.getNeighbor(currentDirection);

                processNextLocation(locationStraight, currentDirection, neighbours, lineLength + 1);
            }

            var right = currentDirection.rotateClockWise();

            var locationRight = currentLocation.getNeighbor(right);

            processNextLocation(locationRight, right, neighbours, 1);

            return neighbours;
        }

        private void processNextLocation(Coordinate newLocation, Direction newDirection,
                                         HashMap<StepCrucible, Long> neighbours,
                                         int newLength) {
            if (grid.isCoordinateInGrid(newLocation)) {
                var key = new StepKey(newLocation, newDirection, newLength);
                if (visitedSteps.containsKey(key)) {
                    neighbours.put(visitedSteps.get(key), Long.valueOf(grid.get(newLocation)));
                } else {
                    var newDirections = new ArrayList<>(directions);
                    newDirections.add(new Vector(newLocation, newDirection));
                    var step = new StepCrucible(newLocation, newDirection, destination, grid, newLength, newDirections,
                                                visitedSteps);
                    visitedSteps.put(key, step);
                    neighbours.put(step, Long.valueOf(grid.get(newLocation)));
                }
            }
        }

        @Override
        public int compareTo(StepCrucible o) {
            return Comparator.comparingLong((StepCrucible stepCrucible) -> stepCrucible.getDistance() +
                                                                           stepCrucible.currentLocation.getDistanceTo(
                                                                                   stepCrucible.destination))
                             .compare(this, o);
        }

        @Override
        public void printStateInformation() {
            for (var y = 0; y <= grid.getEndy(); y++) {
                for (var x = 0; x <= grid.getEndx(); x++) {
                    var coordinate = new Coordinate(x, y);
                    var direction = directions.stream()
                                              .filter(vector -> vector.coordinate().equals(coordinate))
                                              .toList();
                    if (direction.size() == 1 || direction.stream().map(Vector::direction).distinct().count() == 1) {
                        switch (direction.getFirst().direction()) {
                            case LEFT -> System.out.print("<");
                            case RIGHT -> System.out.print(">");
                            case UP -> System.out.print("^");
                            case DOWN -> System.out.print("v");
                        }
                    } else if (direction.size() > 1) {
                        System.out.print("x");
                    } else {
                        System.out.print(grid.get(coordinate));
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static class StepUltraCrucible extends Node<StepUltraCrucible> {
        private final Coordinate currentLocation;
        private final Direction currentDirection;
        private final Coordinate destination;
        private final Grid<Integer> grid;
        private final int lineLength;
        private final List<Vector> directions;
        private final Map<StepKey, StepUltraCrucible> visitedSteps;

        private StepUltraCrucible(Coordinate currentLocation, Direction currentDirection, Coordinate destination,
                                  Grid<Integer> grid, int lineLength, List<Vector> directions,
                                  Map<StepKey, StepUltraCrucible> visitedSteps) {
            this.currentLocation = currentLocation;
            this.currentDirection = currentDirection;
            this.destination = destination;
            this.grid = grid;
            this.lineLength = lineLength;
            this.directions = directions;
            this.visitedSteps = visitedSteps;
        }

        @Override
        public Map<StepUltraCrucible, Long> getNeighbors() {
            var neighbours = new HashMap<StepUltraCrucible, Long>();

            if (lineLength > 3) {
                var left = currentDirection.rotateCounterClockWise();

                var locationLeft = currentLocation.getNeighbor(left);

                processNextLocation(locationLeft, left, neighbours, 1);
            }

            if (lineLength < 10) {
                var locationStraight = currentLocation.getNeighbor(currentDirection);

                processNextLocation(locationStraight, currentDirection, neighbours, lineLength + 1);
            }

            if (lineLength > 3) {
                var right = currentDirection.rotateClockWise();

                var locationRight = currentLocation.getNeighbor(right);

                processNextLocation(locationRight, right, neighbours, 1);
            }

            return neighbours;
        }

        private void processNextLocation(Coordinate newLocation, Direction newDirection,
                                         HashMap<StepUltraCrucible, Long> neighbours,
                                         int newLength) {
            if (grid.isCoordinateInGrid(newLocation)) {
                var key = new StepKey(newLocation, newDirection, newLength);
                if (visitedSteps.containsKey(key)) {
                    neighbours.put(visitedSteps.get(key), Long.valueOf(grid.get(newLocation)));
                } else {
                    var newDirections = new ArrayList<>(directions);
                    newDirections.add(new Vector(newLocation, newDirection));
                    var step = new StepUltraCrucible(newLocation, newDirection, destination, grid, newLength,
                                                     newDirections,
                                                     visitedSteps);
                    visitedSteps.put(key, step);
                    neighbours.put(step, Long.valueOf(grid.get(newLocation)));
                }
            }
        }

        @Override
        public int compareTo(StepUltraCrucible o) {
            return Comparator.comparingLong((StepUltraCrucible stepUltraCrucible) -> stepUltraCrucible.getDistance() +
                                                                                     stepUltraCrucible.currentLocation.getDistanceTo(
                                                                                             stepUltraCrucible.destination))
                             .compare(this, o);
        }

        @Override
        public void printStateInformation() {
            for (var y = 0; y <= grid.getEndy(); y++) {
                for (var x = 0; x <= grid.getEndx(); x++) {
                    var coordinate = new Coordinate(x, y);
                    var direction = directions.stream()
                                              .filter(vector -> vector.coordinate().equals(coordinate))
                                              .toList();
                    if (direction.size() == 1 || direction.stream().map(Vector::direction).distinct().count() == 1) {
                        switch (direction.getFirst().direction()) {
                            case LEFT -> System.out.print("<");
                            case RIGHT -> System.out.print(">");
                            case UP -> System.out.print("^");
                            case DOWN -> System.out.print("v");
                        }
                    } else if (direction.size() > 1) {
                        System.out.print("x");
                    } else {
                        System.out.print(grid.get(coordinate));
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private record StepKey(Coordinate location, Direction direction, int length) {
    }
}
