package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;

import java.util.stream.Stream;

public class Day4 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var xSpots = grid.findValues('X');

        var amount = 0;

        for (var xSpot : xSpots) {
            var neighbours = xSpot.getAllNeighbours();
            for (var neighbour : neighbours) {
                Character M = grid.get(neighbour);
                if (M != null && M == 'M') {
                    var direction = xSpot.getDirection(neighbour);
                    Character A = grid.get(neighbour.getNeighbor(direction));
                    Character S = grid.get(neighbour.getNeighbor(direction).getNeighbor(direction));
                    if (A != null && A == 'A' &&
                        S != null && S == 'S') {
                        amount++;
                    };
                }
            }
        }

        return amount;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var aSpots = grid.findValues('A');

        var amount = 0;

        for (var aSpot : aSpots) {
            var topLeft = aSpot.topLeft();
            var topRight = aSpot.topRight();
            var bottomRight = aSpot.bottomRight();
            var bottomLeft = aSpot.bottomLeft();

            if (grid.isCoordinateInGrid(topLeft) &&
                grid.isCoordinateInGrid(topRight) &&
                grid.isCoordinateInGrid(bottomRight) &&
                grid.isCoordinateInGrid(bottomLeft)) {
                var topLeftChar = grid.get(topLeft);
                var topRightChar = grid.get(topRight);
                var bottomRightChar = grid.get(bottomRight);
                var bottomLeftChar = grid.get(bottomLeft);

                if (topLeftChar == 'M' && topRightChar == 'M' && bottomRightChar == 'S' && bottomLeftChar == 'S') {
                    amount++;
                }
                if (topLeftChar == 'M' && bottomLeftChar == 'M' && topRightChar == 'S' && bottomRightChar == 'S') {
                    amount++;
                }
                if (topLeftChar == 'S' && topRightChar == 'S' && bottomRightChar == 'M' && bottomLeftChar == 'M') {
                    amount++;
                }
                if (topLeftChar == 'S' && bottomLeftChar == 'S' && topRightChar == 'M' && bottomRightChar == 'M') {
                    amount++;
                }
            }
        }

        return amount;
    }
}
