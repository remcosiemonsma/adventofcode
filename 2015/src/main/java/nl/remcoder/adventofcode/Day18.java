package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.stream.Stream;

public class Day18 {
    public long handlePart1(Stream<String> input) {
        var grid = GridFactory.createBooleanGridFromInput(input);

        for (int i = 0; i < 100; i++) {
            var newGrid = new Grid<Boolean>(grid.getStartx(), grid.getStarty(), grid.getEndx(), grid.getEndy());

            for (var x = grid.getStartx(); x <= grid.getEndx(); x++) {
                for (var y = grid.getStarty(); y <= grid.getEndy(); y++) {
                    switchState(grid, newGrid, x, y);
                }
            }

            grid = newGrid;
        }

        return grid.countElements(true);
    }

    public long handlePart2(Stream<String> input) {
        var grid = GridFactory.createBooleanGridFromInput(input);

        for (int i = 0; i < 100; i++) {
            var newGrid = new Grid<Boolean>(grid.getStartx(), grid.getStarty(), grid.getEndx(), grid.getEndy());

            for (var x = grid.getStartx(); x <= grid.getEndx(); x++) {
                for (var y = grid.getStarty(); y <= grid.getEndy(); y++) {
                    if (x == 0 && y == 0 ||
                        x == 0 && y == grid.getEndy() ||
                        x == grid.getEndx() && y == 0 ||
                        x == grid.getEndx() && y == grid.getEndy()) {
                        newGrid.set(new Coordinate(x, y), true);
                    } else {
                        switchState(grid, newGrid, x, y);
                    }
                }
            }

            grid = newGrid;
        }

        return grid.countElements(true);
    }

    private void switchState(Grid<Boolean> grid, Grid<Boolean> newGrid, int x, int y) {
        var coordinate = new Coordinate(x, y);

        var above = coordinate.above();
        var topRight = coordinate.topRight();
        var right = coordinate.right();
        var bottomRight = coordinate.bottomRight();
        var below = coordinate.below();
        var bottomLeft = coordinate.bottomLeft();
        var left = coordinate.left();
        var topLeft = coordinate.topLeft();

        int amountOfLivingNeighbours = 0;

        if (Boolean.TRUE.equals(grid.get(above))) {
            amountOfLivingNeighbours++;
        }
        if (Boolean.TRUE.equals(grid.get(topRight))) {
            amountOfLivingNeighbours++;
        }
        if (Boolean.TRUE.equals(grid.get(right))) {
            amountOfLivingNeighbours++;
        }
        if (Boolean.TRUE.equals(grid.get(bottomRight))) {
            amountOfLivingNeighbours++;
        }
        if (Boolean.TRUE.equals(grid.get(below))) {
            amountOfLivingNeighbours++;
        }
        if (Boolean.TRUE.equals(grid.get(bottomLeft))) {
            amountOfLivingNeighbours++;
        }
        if (Boolean.TRUE.equals(grid.get(left))) {
            amountOfLivingNeighbours++;
        }
        if (Boolean.TRUE.equals(grid.get(topLeft))) {
            amountOfLivingNeighbours++;
        }

        if (grid.get(coordinate)) {
            if (amountOfLivingNeighbours == 2 || amountOfLivingNeighbours == 3) {
                newGrid.set(coordinate, true);
            } else {
                newGrid.set(coordinate, false);
            }
        } else {
            if (amountOfLivingNeighbours == 3) {
                newGrid.set(coordinate, true);
            } else {
                newGrid.set(coordinate, false);
            }
        }
    }
}
