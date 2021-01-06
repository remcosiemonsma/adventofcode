package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day6 {
    public int handlePart1(Stream<String> input) {
        boolean[][] grid = new boolean[1000][1000];

        input.map(this::mapStringToInstruction)
             .forEach(instruction -> {
                 switch (instruction.operation) {
                     case TOGGLE:
                         toggle(grid, instruction);
                         break;
                     case TURN_ON:
                         turnon(grid, instruction);
                         break;
                     case TURN_OFF:
                         turnoff(grid, instruction);
                         break;
                 }
             });


        return countActivePixels(grid);
    }

    public int handlePart2(Stream<String> input) {
        int[][] grid = new int[1000][1000];

        input.map(this::mapStringToInstruction)
             .forEach(instruction -> {
                 switch (instruction.operation) {
                     case TOGGLE:
                         toggle(grid, instruction);
                         break;
                     case TURN_ON:
                         turnon(grid, instruction);
                         break;
                     case TURN_OFF:
                         turnoff(grid, instruction);
                         break;
                 }
             });

        return countPixelValue(grid);
    }

    private int countPixelValue(int[][] grid) {
        int count = 0;
        for (int[] row : grid) {
            for (int pixel : row) {
                count += pixel;
            }
        }
        return count;
    }

    private int countActivePixels(boolean[][] grid) {
        int count = 0;
        for (boolean[] row : grid) {
            for (boolean pixel : row) {
                if (pixel) {
                    count++;
                }
            }
        }
        return count;
    }

    private void toggle(boolean[][] grid, Instruction instruction) {
        for (int x = instruction.startx; x <= instruction.endx; x++) {
            for (int y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] = !grid[x][y];
            }
        }
    }

    private void turnon(boolean[][] grid, Instruction instruction) {
        for (int x = instruction.startx; x <= instruction.endx; x++) {
            for (int y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] = true;
            }
        }
    }

    private void turnoff(boolean[][] grid, Instruction instruction) {
        for (int x = instruction.startx; x <= instruction.endx; x++) {
            for (int y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] = false;
            }
        }
    }

    private void toggle(int[][] grid, Instruction instruction) {
        for (int x = instruction.startx; x <= instruction.endx; x++) {
            for (int y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] += 2;
            }
        }
    }

    private void turnon(int[][] grid, Instruction instruction) {
        for (int x = instruction.startx; x <= instruction.endx; x++) {
            for (int y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] += 1;
            }
        }
    }

    private void turnoff(int[][] grid, Instruction instruction) {
        for (int x = instruction.startx; x <= instruction.endx; x++) {
            for (int y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] -= 1;
                if (grid[x][y] < 0) {
                    grid[x][y] = 0;
                }
            }
        }
    }

    private Instruction mapStringToInstruction(String line) {
        String[] instructionparts = line.split("(\\s(?=\\d)| through )");

        String[] start = instructionparts[1].split(",");
        String[] end = instructionparts[2].split(",");

        int startx = Integer.parseInt(start[0]);
        int starty = Integer.parseInt(start[1]);
        int endx = Integer.parseInt(end[0]);
        int endy = Integer.parseInt(end[1]);

        Operation operation = null;

        switch (instructionparts[0]) {
            case "turn on":
                operation = Operation.TURN_ON;
                break;
            case "turn off":
                operation = Operation.TURN_OFF;
                break;
            case "toggle":
                operation = Operation.TOGGLE;
                break;
        }

        return new Instruction(operation, startx, starty, endx, endy);
    }

    private enum Operation {
        TOGGLE,
        TURN_ON,
        TURN_OFF
    }

    private static class Instruction {
        final Operation operation;
        final int startx;
        final int starty;
        final int endx;
        final int endy;

        public Instruction(Operation operation, int startx, int starty, int endx, int endy) {
            this.operation = operation;
            this.startx = startx;
            this.starty = starty;
            this.endx = endx;
            this.endy = endy;
        }
    }
}
