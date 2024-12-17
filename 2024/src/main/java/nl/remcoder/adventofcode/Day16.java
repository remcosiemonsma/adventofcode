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

public class Day16 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var start = grid.findValue('S').orElseThrow(() -> new AssertionError("Eek!"));

        var end = grid.findValue('E').orElseThrow(() -> new AssertionError("Ook!"));

        var visitedPoints = new HashMap<Vector, MazeStep>();

        var mazeStart = new MazeStep(grid, start, Direction.RIGHT, visitedPoints, Set.of(start));

        visitedPoints.put(new Vector(start, Direction.RIGHT), mazeStart);

        mazeStart.setDistance(0);

        return Dijkstra.findShortestDistance(List.of(mazeStart),
                                             node -> ((MazeStep) node).getCurrentPosition().equals(end))
                       .orElseThrow(() -> new AssertionError("Aak!"))
                       .getDistance();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        return (long) filledSeats(grid);
    }

    private static class MazeStep extends Node<MazeStep> {
        private final Grid<Character> grid;
        private final Coordinate currentPosition;
        private final Direction currentDirection;
        private final Map<Vector, MazeStep> visitedStates;
        private final Set<Coordinate> steps;

        private MazeStep(Grid<Character> grid, Coordinate currentPosition,
                         Direction currentDirection, Map<Vector, MazeStep> visitedStates, Set<Coordinate> steps) {
            this.grid = grid;
            this.currentPosition = currentPosition;
            this.currentDirection = currentDirection;
            this.visitedStates = visitedStates;
            this.steps = steps;
        }

        @Override
        public Map<MazeStep, Long> getNeighbors() {
            var neighbors = new HashMap<MazeStep, Long>();

            currentPosition.getStraightNeighbours()
                           .stream()
                           .filter(point -> grid.get(point) != '#')
//                           .filter(point -> !point.equals(currentPosition.getNeighbor(currentDirection.flip())))
//                           .filter(Predicate.not(steps::contains))
                           .forEach(point -> {
                               var nextDirection = currentPosition.getDirection(point);
                               var score = nextDirection == currentDirection ? 1L : 1001L;
                               if (!visitedStates.containsKey(new Vector(point, nextDirection))) {
                                   var newSteps = new HashSet<>(steps);
                                   newSteps.add(point);
                                   var nextStep =
                                           new MazeStep(grid, point, nextDirection, visitedStates,
                                                        newSteps);
                                   visitedStates.put(new Vector(point, nextDirection), nextStep);
                                   neighbors.put(nextStep, score);
                               } else {
                                   neighbors.put(visitedStates.get(new Vector(point, nextDirection)), score);
                               }
                           });

            return neighbors;
        }

        public Coordinate getCurrentPosition() {
            return currentPosition;
        }

        public Set<Coordinate> steps() {
            return steps;
        }

        @Override
        public void printStateInformation() {

        }
    }

    private record State(Coordinate currentPoint, Direction direction, Set<Coordinate> visitedPoints,
                         int currentScore) {
    }

    private int filledSeats(Grid<Character> grid) {
        int shortest = -1;
        var queue = new PriorityQueue<SeatNode>();
        var viewingSpots = new HashSet<Coordinate>();
        var visited = new HashSet<Vector>();

        var start = grid.findValue('S').orElseThrow(() -> new AssertionError("Eek!"));

        var end = grid.findValue('E').orElseThrow(() -> new AssertionError("Ook!"));

        var walls = Set.copyOf(grid.findValues('#'));

        queue.add(new SeatNode(new Vector(start, Direction.RIGHT), 0, null));
        while (!queue.isEmpty()) {
            var current = queue.remove();
            if (current.vector().coordinate().equals(end)) {
                if (shortest == -1) {
                    shortest = current.distance;
                }
                if (shortest == current.distance) {
                    viewingSpots.addAll(current.path());
                }
            }
            visited.add(current.vector());
            for (var next : neighbours(current, walls)) {
                if (!visited.contains(next.vector())) {
                    queue.add(next);
                }
            }
        }
        return viewingSpots.size();
    }

    private Collection<SeatNode> neighbours(SeatNode node, Set<Coordinate> walls) {
        var neighbours = new HashSet<SeatNode>();
        var vector = node.vector();
        var currentHeading = vector.direction();
        var headingClockwise = currentHeading.rotateClockWise();
        neighbours.add(new SeatNode(new Vector(vector.coordinate(), headingClockwise), node.distance + 1000, node));
        var headingCounterClockwise = currentHeading.rotateCounterClockWise();
        neighbours.add(new SeatNode(new Vector(vector.coordinate(), headingCounterClockwise), node.distance + 1000, node));
        var nextPosition = vector.coordinate().getNeighbor(currentHeading);
        if (!walls.contains(nextPosition)) {
            neighbours.add(new SeatNode(new Vector(nextPosition, currentHeading), node.distance + 1, node));
        }
        return neighbours;
    }

    private record SeatNode(Vector vector, int distance, SeatNode previous) implements Comparable<SeatNode> {
        public Collection<Coordinate> path() {
            var path = new HashSet<Coordinate>();
            var nd = this;
            while (nd != null) {
                path.add(nd.vector().coordinate());
                nd = nd.previous;
            }
            return path;
        }

        @Override
        public int compareTo(SeatNode o) {
            return Comparator.comparing(SeatNode::distance)
                             .thenComparing(SeatNode::vector)
                             .compare(this, o);
        }
    }
}
