package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.model.Vector;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day10 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var vectors = determineStartVectors(grid);

        var distance = walk(vectors, grid);

        return distance + 1;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = new Grid<>(input.map(s -> s.chars()
                                              .mapToObj(Pipe::new)
                                              .toArray(Pipe[]::new))
                                   .toArray(Pipe[][]::new));

        var start = determineStartPoint(grid);

        grid.get(start).setLine(true);

        Direction walkingDirection = determineWalkingDirection(grid, start);

        walk(walkingDirection, start, grid);

        floodFill(grid);

        var amountOutsidePoints = grid.findValues(Predicate.not(Pipe::line).and(Pipe::outside)).size();
        var amountInsidePoints = grid.findValues(Predicate.not(Pipe::line).and(Predicate.not(Pipe::outside))).size();

        return Math.min(amountInsidePoints, amountOutsidePoints);
    }

    private int walk(ArrayList<Vector> vectors, Grid<Character> grid) {
        var distance = 0;

        while (vectors.stream().map(Vector::coordinate).distinct().count() != 1) {
            distance++;

            var newVectors = new ArrayList<Vector>();

            for (var vector : vectors) {
                var currentPipe = grid.get(vector.coordinate());
                switch (currentPipe) {
                    case '|' -> {
                        if (vector.direction() == Direction.UP) {
                            newVectors.add(new Vector(vector.coordinate().above(), Direction.UP));
                        } else {
                            newVectors.add(new Vector(vector.coordinate().below(), Direction.DOWN));
                        }
                    }
                    case '-' -> {
                        if (vector.direction() == Direction.LEFT) {
                            newVectors.add(new Vector(vector.coordinate().left(), Direction.LEFT));
                        } else {
                            newVectors.add(new Vector(vector.coordinate().right(), Direction.RIGHT));
                        }
                    }
                    case 'L' -> {
                        if (vector.direction() == Direction.DOWN) {
                            newVectors.add(new Vector(vector.coordinate().right(), Direction.RIGHT));
                        } else {
                            newVectors.add(new Vector(vector.coordinate().above(), Direction.UP));
                        }
                    }
                    case 'J' -> {
                        if (vector.direction() == Direction.DOWN) {
                            newVectors.add(new Vector(vector.coordinate().left(), Direction.LEFT));
                        } else {
                            newVectors.add(new Vector(vector.coordinate().above(), Direction.UP));
                        }
                    }
                    case '7' -> {
                        if (vector.direction() == Direction.UP) {
                            newVectors.add(new Vector(vector.coordinate().left(), Direction.LEFT));
                        } else {
                            newVectors.add(new Vector(vector.coordinate().below(), Direction.DOWN));
                        }
                    }
                    case 'F' -> {
                        if (vector.direction() == Direction.UP) {
                            newVectors.add(new Vector(vector.coordinate().right(), Direction.RIGHT));
                        } else {
                            newVectors.add(new Vector(vector.coordinate().below(), Direction.DOWN));
                        }
                    }
                }
            }

            vectors = newVectors;
        }
        return distance;
    }

    private ArrayList<Vector> determineStartVectors(Grid<Character> grid) {
        var start = grid.findValue('S').orElseThrow(() -> new AssertionError("Eek!"));

        var vectors = new ArrayList<Vector>();

        var top = grid.get(start.above());

        if (top != null && (top == '|' || top == '7' || top == 'F')) {
            vectors.add(new Vector(start.above(), Direction.UP));
        }

        var below = grid.get(start.below());

        if (below != null && (below == '|' || below == 'J' || below == 'L')) {
            vectors.add(new Vector(start.below(), Direction.DOWN));
        }

        var left = grid.get(start.left());

        if (left != null && (left == '-' || left == 'L' || left == 'F')) {
            vectors.add(new Vector(start.left(), Direction.LEFT));
        }

        var right = grid.get(start.right());

        if (right != null && (right == '-' || right == 'J' || right == '7')) {
            vectors.add(new Vector(start.right(), Direction.RIGHT));
        }
        return vectors;
    }

    private void floodFill(Grid<Pipe> grid) {
        grid.findValues(Pipe::line).forEach(point -> grid.get(point).setOutside(false));

        var outsidePoints = grid.findValues(pipe -> pipe.outside() && !pipe.line());

        while (!outsidePoints.isEmpty()) {
            var nextOutsidePoints = new ArrayList<Coordinate>();
            for (var outsidePoint : outsidePoints) {
                for (var neighbor : outsidePoint.getStraightNeighbours()) {
                    if (grid.isCoordinateInGrid(neighbor)) {
                        var pipe = grid.get(neighbor);
                        if (!(pipe.outside() || pipe.line())) {
                            pipe.setOutside(true);
                            nextOutsidePoints.add(neighbor);
                        }
                    }
                }
            }

            outsidePoints = nextOutsidePoints;
        }
    }

    private void walk(Direction walkingDirection, Coordinate start, Grid<Pipe> grid) {
        var next = switch (walkingDirection) {
            case UP -> start.above();
            case LEFT -> start.left();
            case DOWN -> start.below();
            case RIGHT -> start.right();
        };

        while (!next.equals(start)) {
            var currentPipe = grid.get(next);
            currentPipe.setLine(true);
            switch (walkingDirection) {
                case UP -> {
                    if (grid.isCoordinateInGrid(next.left())) {
                        grid.get(next.left()).setOutside(true);
                    }
                    if (currentPipe.symbol() == '|') {
                        next = next.above();
                    } else if (currentPipe.symbol() == 'F') {
                        if (grid.isCoordinateInGrid(next.above())) {
                            grid.get(next.above()).setOutside(true);
                        }
                        next = next.right();
                        walkingDirection = Direction.RIGHT;
                    } else if (currentPipe.symbol() == '7') {
                        next = next.left();
                        walkingDirection = Direction.LEFT;
                    }
                }
                case RIGHT -> {
                    if (grid.isCoordinateInGrid(next.above())) {
                        grid.get(next.above()).setOutside(true);
                    }
                    if (currentPipe.symbol() == '-') {
                        next = next.right();
                    } else if (currentPipe.symbol() == '7') {
                        if (grid.isCoordinateInGrid(next.right())) {
                            grid.get(next.right()).setOutside(true);
                        }
                        next = next.below();
                        walkingDirection = Direction.DOWN;
                    } else if (currentPipe.symbol() == 'J') {
                        next = next.above();
                        walkingDirection = Direction.UP;
                    }
                }
                case DOWN -> {
                    if (grid.isCoordinateInGrid(next.right())) {
                        grid.get(next.right()).setOutside(true);
                    }
                    if (currentPipe.symbol() == '|') {
                        next = next.below();
                    } else if (currentPipe.symbol() == 'J') {
                        if (grid.isCoordinateInGrid(next.below())) {
                            grid.get(next.below()).setOutside(true);
                        }
                        next = next.left();
                        walkingDirection = Direction.LEFT;
                    } else if (currentPipe.symbol() == 'L') {
                        next = next.right();
                        walkingDirection = Direction.RIGHT;
                    }
                }
                case LEFT -> {
                    if (grid.isCoordinateInGrid(next.below())) {
                        grid.get(next.below()).setOutside(true);
                    }
                    if (currentPipe.symbol() == '-') {
                        next = next.left();
                    } else if (currentPipe.symbol() == 'L') {
                        if (grid.isCoordinateInGrid(next.left())) {
                            grid.get(next.left()).setOutside(true);
                        }
                        next = next.above();
                        walkingDirection = Direction.UP;
                    } else if (currentPipe.symbol() == 'F') {
                        next = next.below();
                        walkingDirection = Direction.DOWN;
                    }
                }
            }
        }
    }

    private Direction determineWalkingDirection(Grid<Pipe> grid, Coordinate start) {
        return switch (grid.get(start).symbol()) {
            case 'L' -> {
                if (grid.isCoordinateInGrid(start.left())) {
                    grid.get(start.left()).setOutside(true);
                }
                if (grid.isCoordinateInGrid(start.below())) {
                    grid.get(start.below()).setOutside(true);
                }
                yield Direction.UP;
            }
            case 'J' -> {
                if (grid.isCoordinateInGrid(start.right())) {
                    grid.get(start.right()).setOutside(true);
                }
                if (grid.isCoordinateInGrid(start.below())) {
                    grid.get(start.below()).setOutside(true);
                }
                yield Direction.LEFT;
            }
            case '7' -> {
                if (grid.isCoordinateInGrid(start.right())) {
                    grid.get(start.right()).setOutside(true);
                }
                if (grid.isCoordinateInGrid(start.above())) {
                    grid.get(start.above()).setOutside(true);
                }
                yield Direction.DOWN;
            }
            case 'F' -> {
                if (grid.isCoordinateInGrid(start.left())) {
                    grid.get(start.left()).setOutside(true);
                }
                if (grid.isCoordinateInGrid(start.above())) {
                    grid.get(start.above()).setOutside(true);
                }
                yield Direction.RIGHT;
            }
            default -> throw new AssertionError("Eek!");
        };
    }

    private Coordinate determineStartPoint(Grid<Pipe> grid) {
        var start = grid.findValue(pipe -> pipe.symbol() == 'S')
                        .orElseThrow(() -> new AssertionError("Eek!"));

        grid.get(start).setVisited(true);

        var top = grid.get(start.above());
        var below = grid.get(start.below());
        var left = grid.get(start.left());
        var right = grid.get(start.right());

        if (top != null && (top.symbol() == '|' || top.symbol() == '7' || top.symbol() == 'F')) {
            if (below != null && (below.symbol() == '|' || below.symbol() == 'J' || below.symbol() == 'L')) {
                grid.set(start, new Pipe('|'));
            } else if (left != null && (left.symbol() == '-' || left.symbol() == 'L' || left.symbol() == 'F')) {
                grid.set(start, new Pipe('J'));
            } else if (right != null && (right.symbol() == '-' || right.symbol() == 'J' || right.symbol() == '7')) {
                grid.set(start, new Pipe('L'));
            }
        } else if (below != null && (below.symbol() == '|' || below.symbol() == 'J' || below.symbol() == 'L')) {
            if (left != null && (left.symbol() == '-' || left.symbol() == 'L' || left.symbol() == 'F')) {
                grid.set(start, new Pipe('7'));
            } else if (right != null && (right.symbol() == '-' || right.symbol() == 'J' || right.symbol() == '7')) {
                grid.set(start, new Pipe('F'));
            }
        } else if (left != null && (left.symbol() == '|' || left.symbol() == 'J' || left.symbol() == 'L')) {
            if (right != null && (right.symbol() == '-' || right.symbol() == 'J' || right.symbol() == '7')) {
                grid.set(start, new Pipe('-'));
            }
        }

        if (grid.get(start).symbol() == '|') {
            while (grid.get(start).symbol() == '|') {
                start = start.above();
            }
        } else if (grid.get(start).symbol() == '-') {
            while (grid.get(start).symbol() == '|') {
                start = start.right();
            }
        }
        return start;
    }

    private static class Pipe {
        private final char symbol;
        private boolean line = false;
        private boolean outside = false;
        private boolean visited = false;

        private Pipe(int symbol) {
            this.symbol = (char) symbol;
        }

        public char symbol() {
            return symbol;
        }

        public boolean visited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public boolean line() {
            return line;
        }

        public void setLine(boolean line) {
            this.line = line;
        }

        public boolean outside() {
            return outside;
        }

        public void setOutside(boolean outside) {
            this.outside = outside;
        }

        @Override
        public String toString() {
            if (outside) {
                return "" + symbol;
            } else {
                return "" + symbol;
            }
        }
    }
}
