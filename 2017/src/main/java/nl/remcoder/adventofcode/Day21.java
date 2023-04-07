package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day21 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var operations = input.map(this::parseOperation)
                              .toList();
        
        var grid = generateGrid(5, operations);
        
        return countPixels(grid);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var operations = input.map(this::parseOperation)
                              .toList();

        var grid = generateGrid(18, operations);

        return countPixels(grid);
    }

    private int countPixels(boolean[][] grid) {
        var counter = 0;

        for (var row : grid) {
            for (var pixel : row) {
                if (pixel) {
                    counter++;
                }
            }
        }
        
        return counter;
    }
    
    private boolean[][] generateGrid(int iterations, List<Operation> operations) {
        var grid = new boolean[][]{{false, true, false}, {false, false, true}, {true, true, true}};

        for (var unused = 0; unused < iterations; unused++) {
            if (grid.length % 2 == 0) {
                var newgrid = new boolean[(grid.length / 2) * 3][(grid.length / 2) * 3];
                for (var i = 0; i < grid.length / 2; i++) {
                    for (var j = 0; j < grid.length / 2; j++) {
                        var gridpart = new boolean[2][2];

                        gridpart[0][0] = grid[i * 2][j * 2];
                        gridpart[0][1] = grid[i * 2][(j * 2) + 1];
                        gridpart[1][0] = grid[(i * 2) + 1][j * 2];
                        gridpart[1][1] = grid[(i * 2) + 1][(j * 2) + 1];

                        for (var operation : operations) {
                            if (isOperationMatch(gridpart, operation)) {
                                newgrid[i * 3][j * 3] = operation.output[0][0];
                                newgrid[i * 3][(j * 3) + 1] = operation.output[0][1];
                                newgrid[i * 3][(j * 3) + 2] = operation.output[0][2];
                                newgrid[(i * 3) + 1][j * 3] = operation.output[1][0];
                                newgrid[(i * 3) + 1][(j * 3) + 1] = operation.output[1][1];
                                newgrid[(i * 3) + 1][(j * 3) + 2] = operation.output[1][2];
                                newgrid[(i * 3) + 2][j * 3] = operation.output[2][0];
                                newgrid[(i * 3) + 2][(j * 3) + 1] = operation.output[2][1];
                                newgrid[(i * 3) + 2][(j * 3) + 2] = operation.output[2][2];
                                break;
                            }
                        }
                    }
                }
                grid = newgrid;
            } else {
                var newgrid = new boolean[(grid.length / 3) * 4][(grid.length / 3) * 4];
                for (var i = 0; i < grid.length / 3; i++) {
                    for (var j = 0; j < grid.length / 3; j++) {
                        var gridpart = new boolean[3][3];

                        gridpart[0][0] = grid[i * 3][j * 3];
                        gridpart[0][1] = grid[i * 3][(j * 3) + 1];
                        gridpart[0][2] = grid[i * 3][(j * 3) + 2];
                        gridpart[1][0] = grid[(i * 3) + 1][j * 3];
                        gridpart[1][1] = grid[(i * 3) + 1][(j * 3) + 1];
                        gridpart[1][2] = grid[(i * 3) + 1][(j * 3) + 2];
                        gridpart[2][0] = grid[(i * 3) + 2][j * 3];
                        gridpart[2][1] = grid[(i * 3) + 2][(j * 3) + 1];
                        gridpart[2][2] = grid[(i * 3) + 2][(j * 3) + 2];

                        for (var operation : operations) {
                            if (isOperationMatch(gridpart, operation)) {
                                newgrid[i * 4][j * 4] = operation.output[0][0];
                                newgrid[i * 4][(j * 4) + 1] = operation.output[0][1];
                                newgrid[i * 4][(j * 4) + 2] = operation.output[0][2];
                                newgrid[i * 4][(j * 4) + 3] = operation.output[0][3];
                                newgrid[(i * 4) + 1][j * 4] = operation.output[1][0];
                                newgrid[(i * 4) + 1][(j * 4) + 1] = operation.output[1][1];
                                newgrid[(i * 4) + 1][(j * 4) + 2] = operation.output[1][2];
                                newgrid[(i * 4) + 1][(j * 4) + 3] = operation.output[1][3];
                                newgrid[(i * 4) + 2][j * 4] = operation.output[2][0];
                                newgrid[(i * 4) + 2][(j * 4) + 1] = operation.output[2][1];
                                newgrid[(i * 4) + 2][(j * 4) + 2] = operation.output[2][2];
                                newgrid[(i * 4) + 2][(j * 4) + 3] = operation.output[2][3];
                                newgrid[(i * 4) + 3][j * 4] = operation.output[3][0];
                                newgrid[(i * 4) + 3][(j * 4) + 1] = operation.output[3][1];
                                newgrid[(i * 4) + 3][(j * 4) + 2] = operation.output[3][2];
                                newgrid[(i * 4) + 3][(j * 4) + 3] = operation.output[3][3];
                                break;
                            }
                        }
                    }
                }
                grid = newgrid;
            }
        }
        
        return grid;
    }

    private boolean isOperationMatch(boolean[][] gridpart, Operation operation) {
        if (Arrays.deepEquals(gridpart, operation.input)) {
            return true;
        }
        if (gridpart.length == 2) {
            var tmp = gridpart;

            for (var i = 0; i < 3; i++) {
                tmp = rotateGrid(tmp);
                if (Arrays.deepEquals(tmp, operation.input)) {
                    return true;
                }
            }
        } else {
            var tmp = gridpart;

            for (var i = 0; i < 3; i++) {
                tmp = rotateGrid(tmp);
                if (Arrays.deepEquals(tmp, operation.input)) {
                    return true;
                }
            }

            tmp = flipGridHorizontal(gridpart);

            for (var i = 0; i < 3; i++) {
                tmp = rotateGrid(tmp);
                if (Arrays.deepEquals(tmp, operation.input)) {
                    return true;
                }
            }

            tmp = flipGridVertical(gridpart);

            for (var i = 0; i < 3; i++) {
                tmp = rotateGrid(tmp);
                if (Arrays.deepEquals(tmp, operation.input)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean[][] rotateGrid(boolean[][] grid) {
        var value = new boolean[grid.length][grid.length];

        if (grid.length == 2) {
            value[0][0] = grid[0][1];
            value[0][1] = grid[1][1];
            value[1][0] = grid[0][0];
            value[1][1] = grid[1][0];
        } else {
            value[0][0] = grid[0][2];
            value[0][1] = grid[1][2];
            value[0][2] = grid[2][2];
            value[1][0] = grid[0][1];
            value[1][1] = grid[1][1];
            value[1][2] = grid[2][1];
            value[2][0] = grid[0][0];
            value[2][1] = grid[1][0];
            value[2][2] = grid[2][0];
        }

        return value;
    }

    private boolean[][] flipGridHorizontal(boolean[][] grid) {
        var value = new boolean[grid.length][grid.length];

        if (grid.length == 2) {
            value[0] = grid[1];
            value[1] = grid[0];
        } else {
            value[0] = grid[2];
            value[1] = grid[1];
            value[2] = grid[0];
        }

        return value;
    }

    private static boolean[][] flipGridVertical(boolean[][] grid) {
        var value = new boolean[grid.length][grid.length];

        if (grid.length == 2) {
            value[0][0] = grid[0][1];
            value[1][0] = grid[1][1];
            value[0][1] = grid[0][0];
            value[1][1] = grid[1][0];
        } else {
            value[0][0] = grid[0][2];
            value[0][1] = grid[0][1];
            value[0][2] = grid[0][0];
            value[1][0] = grid[1][2];
            value[1][1] = grid[1][1];
            value[1][2] = grid[1][0];
            value[2][0] = grid[2][2];
            value[2][1] = grid[2][1];
            value[2][2] = grid[2][0];
        }

        return value;
    }
    
    private Operation parseOperation(String line) {
        var parts = line.split(" => ");

        var pixelsinput = parts[0].split("/");

        var input = new boolean[pixelsinput.length][pixelsinput.length];
        for (int i = 0; i < pixelsinput.length; i++) {
            for (int j = 0; j < pixelsinput.length; j++) {
                input[i][j] = pixelsinput[i].charAt(j) == '#';
            }
        }

        var pixelsoutput = parts[1].split("/");

        var output = new boolean[pixelsoutput.length][pixelsoutput.length];
        for (int i = 0; i < pixelsoutput.length; i++) {
            for (int j = 0; j < pixelsoutput.length; j++) {
                output[i][j] = pixelsoutput[i].charAt(j) == '#';
            }
        }
        
        return new Operation(input, output);
    }

    private record Operation(boolean[][] input, boolean[][] output) {
    }
}
