package nl.remcoder.adventofcode.day21;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        boolean[][] grid = new boolean[][]{{false, true, false}, {false, false, true}, {true, true, true}};

        List<Operation> operations = new ArrayList<>();

        Files.lines(Paths.get(ClassLoader.getSystemResource("day21/input").toURI())).forEach(s -> {
            String[] parts = s.split(" => ");
            Operation operation = new Operation();

            String[] pixelsinput = parts[0].split("/");

            operation.input = new boolean[pixelsinput.length][pixelsinput.length];
            for (int i = 0; i < pixelsinput.length; i++) {
                for (int j = 0; j < pixelsinput.length; j++) {
                    operation.input[i][j] = pixelsinput[i].charAt(j) == '#';
                }
            }

            String[] pixelsoutput = parts[1].split("/");

            operation.output = new boolean[pixelsoutput.length][pixelsoutput.length];
            for (int i = 0; i < pixelsoutput.length; i++) {
                for (int j = 0; j < pixelsoutput.length; j++) {
                    operation.output[i][j] = pixelsoutput[i].charAt(j) == '#';
                }
            }

            operations.add(operation);
        });

        for (int unused = 0; unused < 5; unused++) {
            if (grid.length % 2 == 0) {
                boolean[][] newgrid = new boolean[(grid.length / 2) * 3][(grid.length / 2) * 3];
                for (int i = 0; i < grid.length / 2; i++) {
                    for (int j = 0; j < grid.length / 2; j++) {
                        boolean[][] gridpart = new boolean[2][2];

                        gridpart[0][0] = grid[i * 2][j * 2];
                        gridpart[0][1] = grid[i * 2][(j * 2) + 1];
                        gridpart[1][0] = grid[(i * 2) + 1][j * 2];
                        gridpart[1][1] = grid[(i * 2) + 1][(j * 2) + 1];

                        for (Operation operation : operations) {
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
                boolean[][] newgrid = new boolean[(grid.length / 3) * 4][(grid.length / 3) * 4];
                for (int i = 0; i < grid.length / 3; i++) {
                    for (int j = 0; j < grid.length / 3; j++) {
                        boolean[][] gridpart = new boolean[3][3];

                        gridpart[0][0] = grid[i * 3][j * 3];
                        gridpart[0][1] = grid[i * 3][(j * 3) + 1];
                        gridpart[0][2] = grid[i * 3][(j * 3) + 2];
                        gridpart[1][0] = grid[(i * 3) + 1][j * 3];
                        gridpart[1][1] = grid[(i * 3) + 1][(j * 3) + 1];
                        gridpart[1][2] = grid[(i * 3) + 1][(j * 3) + 2];
                        gridpart[2][0] = grid[(i * 3) + 2][j * 3];
                        gridpart[2][1] = grid[(i * 3) + 2][(j * 3) + 1];
                        gridpart[2][2] = grid[(i * 3) + 2][(j * 3) + 2];

                        for (Operation operation : operations) {
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

        int counter = 0;

        for (boolean[] row : grid) {
            for (boolean pixel : row) {
                if (pixel) {
                    counter++;
                }
            }
        }

        System.out.println(counter);
    }

    private static boolean isOperationMatch(boolean[][] gridpart, Operation operation) {
        if (Arrays.deepEquals(gridpart, operation.input)) {
            return true;
        }
        if (gridpart.length == 2) {
            boolean[][] tmp = gridpart;

            for (int i = 0; i < 3; i++) {
                tmp = rotateGrid(tmp);
                if (Arrays.deepEquals(tmp, operation.input)) {
                    return true;
                }
            }
        } else {
            boolean[][] tmp = gridpart;

            for (int i = 0; i < 3; i++) {
                tmp = rotateGrid(tmp);
                if (Arrays.deepEquals(tmp, operation.input)) {
                    return true;
                }
            }

            tmp = flipGridHorizontal(gridpart);

            for (int i = 0; i < 3; i++) {
                tmp = rotateGrid(tmp);
                if (Arrays.deepEquals(tmp, operation.input)) {
                    return true;
                }
            }

            tmp = flipGridVertical(gridpart);

            for (int i = 0; i < 3; i++) {
                tmp = rotateGrid(tmp);
                if (Arrays.deepEquals(tmp, operation.input)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean[][] rotateGrid(boolean[][] grid) {
        boolean[][] value = new boolean[grid.length][grid.length];

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

    private static boolean[][] flipGridHorizontal(boolean[][] grid) {
        boolean[][] value = new boolean[grid.length][grid.length];

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
        boolean[][] value = new boolean[grid.length][grid.length];

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

    static class Operation {
        boolean[][] input;
        boolean[][] output;
    }
}
