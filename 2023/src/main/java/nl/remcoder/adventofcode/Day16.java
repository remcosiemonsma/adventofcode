package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.model.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Day16 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var startVector = new Vector(Coordinate.ORIGIN, Direction.RIGHT);

        return countTilesForStartVector(startVector, grid);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        List<Vector> startVectors = new ArrayList<>();

        for (var x = 0; x <= grid.getEndx(); x++) {
            startVectors.add(new Vector(new Coordinate(x, 0), Direction.DOWN));
            startVectors.add(new Vector(new Coordinate(x, grid.getEndy()), Direction.UP));
        }
        for (var y = 0; y <= grid.getEndy(); y++) {
            startVectors.add(new Vector(new Coordinate(0, y), Direction.RIGHT));
            startVectors.add(new Vector(new Coordinate(grid.getEndx(), y), Direction.LEFT));
        }

        return startVectors.stream()
                           .parallel()
                           .mapToInt(startVector -> countTilesForStartVector(startVector, grid))
                           .max()
                           .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int countTilesForStartVector(Vector startVector, Grid<Character> grid) {
        var visitedVectors = new HashSet<Vector>();

        walkGrid(startVector, grid, visitedVectors);

        return (int) visitedVectors.stream()
                                   .map(Vector::coordinate)
                                   .distinct()
                                   .count();
    }

    private void walkGrid(Vector start, Grid<Character> grid, Set<Vector> visitedVectors) {
        var currentVector = start;

        while (grid.isCoordinateInGrid(currentVector.coordinate()) &&
               visitedVectors.add(currentVector)) {
            var tile = grid.get(currentVector.coordinate());
            if (tile == '.') {
                currentVector = new Vector(currentVector.coordinate()
                                                        .getNeighbor(currentVector.direction()),
                                           currentVector.direction());
            } else if (tile == '/') {
                Direction newDirection = switch (currentVector.direction()) {
                    case UP -> Direction.RIGHT;
                    case LEFT -> Direction.DOWN;
                    case DOWN -> Direction.LEFT;
                    case RIGHT -> Direction.UP;
                    case UPLEFT, UPRIGHT, DOWNRIGHT, DOWNLEFT -> throw new AssertionError("Not supposed to happen!");
                };
                currentVector = new Vector(currentVector.coordinate()
                                                        .getNeighbor(newDirection),
                                           newDirection);
            } else if (tile == '\\') {
                Direction newDirection = switch (currentVector.direction()) {
                    case UP -> Direction.LEFT;
                    case LEFT -> Direction.UP;
                    case DOWN -> Direction.RIGHT;
                    case RIGHT -> Direction.DOWN;
                    case UPLEFT, UPRIGHT, DOWNRIGHT, DOWNLEFT -> throw new AssertionError("Not supposed to happen!");
                };
                currentVector = new Vector(currentVector.coordinate()
                                                        .getNeighbor(newDirection),
                                           newDirection);
            } else if (tile == '-') {
                if (currentVector.direction() == Direction.RIGHT ||
                    currentVector.direction() == Direction.LEFT) {
                    currentVector = new Vector(currentVector.coordinate()
                                                            .getNeighbor(currentVector.direction()),
                                               currentVector.direction());
                } else {
                    var vectorLeft = new Vector(currentVector.coordinate().left(), Direction.LEFT);
                    var vectorRight = new Vector(currentVector.coordinate().right(), Direction.RIGHT);
                    walkGrid(vectorLeft, grid, visitedVectors);
                    walkGrid(vectorRight, grid, visitedVectors);
                    break;
                }
            } else if (tile == '|') {
                if (currentVector.direction() == Direction.DOWN ||
                    currentVector.direction() == Direction.UP) {
                    currentVector = new Vector(currentVector.coordinate()
                                                            .getNeighbor(currentVector.direction()),
                                               currentVector.direction());
                } else {
                    var vectorDown = new Vector(currentVector.coordinate().below(), Direction.DOWN);
                    var vectorUp = new Vector(currentVector.coordinate().above(), Direction.UP);
                    walkGrid(vectorUp, grid, visitedVectors);
                    walkGrid(vectorDown, grid, visitedVectors);
                    break;
                }
            }
        }
    }
}
