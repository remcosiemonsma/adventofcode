package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Day3 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = new Grid<>(input.map(s -> s.chars()
                                              .mapToObj(value -> (char) value)
                                              .toArray(Character[]::new))
                                   .toArray(Character[][]::new));

        var numberBuilder = new StringBuilder();
        var symbolAdjacent = false;
        var buildingNumber = false;
        var sum = 0;

        for (var y = 0; y <= grid.getEndy(); y++) {
            for (var x = 0; x <= grid.getEndx(); x++) {
                var coordinate = new Coordinate(x, y);
                var c = grid.get(coordinate);

                if (buildingNumber) {
                    if (Character.isDigit(c)) {
                        if (!symbolAdjacent) {
                            symbolAdjacent = isSymbolAdjacent(coordinate, grid);
                        }
                        numberBuilder.append(c);
                    } else {
                        if (symbolAdjacent) {
                            var number = Integer.parseInt(numberBuilder.toString());
                            sum += number;
                            System.out.print("\u001B[32m" + number);
                        } else {
                            System.out.print("\u001B[31m" + numberBuilder);
                        }

                        if (c.equals('.')) {
                            System.out.print("\u001B[37m.");
                        } else {
                            System.out.print("\u001B[33m" + c);
                        }

                        buildingNumber = false;
                        symbolAdjacent = false;
                        numberBuilder = new StringBuilder();
                    }
                } else {
                    if (Character.isDigit(c)) {
                        buildingNumber = true;
                        numberBuilder.append(c);
                        symbolAdjacent = isSymbolAdjacent(coordinate, grid);
                    } else {
                        if (c.equals('.')) {
                            System.out.print("\u001B[37m.");
                        } else {
                            System.out.print("\u001B[33m" + c);
                        }
                    }
                }
            }
            if (symbolAdjacent) {
                var number = Integer.parseInt(numberBuilder.toString());
                sum += number;
                System.out.print("\u001B[32m" + number);
            } else {
                System.out.print("\u001B[31m" + numberBuilder);
            }
            buildingNumber = false;
            symbolAdjacent = false;
            numberBuilder = new StringBuilder();
            System.out.println();
        }

        return sum;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = new Grid<>(input.map(s -> s.chars()
                                              .mapToObj(value -> (char) value)
                                              .toArray(Character[]::new))
                                   .toArray(Character[][]::new));

        var possibleGears = grid.findValues('*');

        var sum = 0;

        for (var possibleGear : possibleGears) {
            var numbers = new ArrayList<Integer>();
            if (grid.get(possibleGear.above()).equals('.') &&
                Character.isDigit(grid.get(possibleGear.topLeft())) &&
                Character.isDigit(grid.get(possibleGear.topRight()))) {
                findAndAddNumber(possibleGear.topLeft(), grid, numbers);
                findAndAddNumber(possibleGear.topRight(), grid, numbers);
            } else {
                possibleGear.getTopNeighbours()
                            .stream()
                            .filter(neighbour -> Character.isDigit(grid.get(neighbour)))
                            .findFirst()
                            .ifPresent(coordinate -> findAndAddNumber(coordinate, grid, numbers));
            }
            if (grid.get(possibleGear.below()).equals('.') &&
                Character.isDigit(grid.get(possibleGear.bottomLeft())) &&
                Character.isDigit(grid.get(possibleGear.bottomRight()))) {
                findAndAddNumber(possibleGear.bottomLeft(), grid, numbers);
                findAndAddNumber(possibleGear.bottomRight(), grid, numbers);
            } else {
                possibleGear.getBottomNeighbours()
                            .stream()
                            .filter(neighbour -> Character.isDigit(grid.get(neighbour)))
                            .findFirst()
                            .ifPresent(coordinate -> findAndAddNumber(coordinate, grid, numbers));
            }
            if (Character.isDigit(grid.get(possibleGear.left()))) {
                findAndAddNumber(possibleGear.left(), grid, numbers);
            }
            if (Character.isDigit(grid.get(possibleGear.right()))) {
                findAndAddNumber(possibleGear.right(), grid, numbers);
            }
            if (numbers.size() == 2) {
                sum += numbers.get(0) * numbers.get(1);
            }
        }

        return sum;
    }

    private void findAndAddNumber(Coordinate position, Grid<Character> grid,
                                  ArrayList<Integer> numbers) {
        while (grid.isCoordinateInGrid(position) && Character.isDigit(grid.get(position))) {
            position = position.left();
        }
        position = position.right();
        var numberBuilder = new StringBuilder();
        while (grid.isCoordinateInGrid(position) && Character.isDigit(grid.get(position))) {
            numberBuilder.append(grid.get(position));
            position = position.right();
        }
        numbers.add(Integer.parseInt(numberBuilder.toString()));
    }

    private boolean isSymbolAdjacent(Coordinate coordinate, Grid<Character> grid) {
        for (var neighbour : coordinate.getAllNeighbours()) {
            if (grid.isCoordinateInGrid(neighbour)) {
                var possibleSymbol = grid.get(neighbour);
                if (!possibleSymbol.equals('.') && !Character.isDigit(possibleSymbol)) {
                    return true;
                }
            }
        }
        return false;
    }
}
