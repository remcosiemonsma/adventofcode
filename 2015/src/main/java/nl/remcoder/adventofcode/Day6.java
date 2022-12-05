package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day6 {
    public int handlePart1(Stream<String> input) {
        var grid = new boolean[1000][1000];

        input.map(this::mapStringToInstruction)
             .forEach(instruction -> {
                 switch (instruction.operation) {
                     case TOGGLE -> toggle(grid, instruction);
                     case TURN_ON -> turnon(grid, instruction);
                     case TURN_OFF -> turnoff(grid, instruction);
                 }
             });


        return countActivePixels(grid);
    }

    public int handlePart2(Stream<String> input) {
        var grid = new int[1000][1000];

        input.map(this::mapStringToInstruction)
             .forEach(instruction -> {
                 switch (instruction.operation) {
                     case TOGGLE -> toggle(grid, instruction);
                     case TURN_ON -> turnon(grid, instruction);
                     case TURN_OFF -> turnoff(grid, instruction);
                 }
             });

        return countPixelValue(grid);
    }

    private int countPixelValue(int[][] grid) {
        var count = 0;
        for (var row : grid) {
            for (var pixel : row) {
                count += pixel;
            }
        }
        return count;
    }

    private int countActivePixels(boolean[][] grid) {
        var count = 0;
        for (var row : grid) {
            for (var pixel : row) {
                if (pixel) {
                    count++;
                }
            }
        }
        return count;
    }

    private void toggle(boolean[][] grid, Instruction instruction) {
        for (var x = instruction.startx; x <= instruction.endx; x++) {
            for (var y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] = !grid[x][y];
            }
        }
    }

    private void turnon(boolean[][] grid, Instruction instruction) {
        for (var x = instruction.startx; x <= instruction.endx; x++) {
            for (var y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] = true;
            }
        }
    }

    private void turnoff(boolean[][] grid, Instruction instruction) {
        for (var x = instruction.startx; x <= instruction.endx; x++) {
            for (var y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] = false;
            }
        }
    }

    private void toggle(int[][] grid, Instruction instruction) {
        for (var x = instruction.startx; x <= instruction.endx; x++) {
            for (var y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] += 2;
            }
        }
    }

    private void turnon(int[][] grid, Instruction instruction) {
        for (var x = instruction.startx; x <= instruction.endx; x++) {
            for (var y = instruction.starty; y <= instruction.endy; y++) {
                grid[x][y] += 1;
            }
        }
    }

    private void turnoff(int[][] grid, Instruction instruction) {
        for (var x = instruction.startx; x <= instruction.endx; x++) {
            for (var y = instruction.starty; y <= instruction.endy; y++) {
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

        Operation operation = switch (instructionparts[0]) {
            case "turn on" -> Operation.TURN_ON;
            case "turn off" -> Operation.TURN_OFF;
            case "toggle" -> Operation.TOGGLE;
            default -> null;
        };

        return new Instruction(operation, startx, starty, endx, endy);
    }

    private enum Operation {
        TOGGLE,
        TURN_ON,
        TURN_OFF
    }

    private record Instruction(Operation operation, int startx, int starty, int endx, int endy) {}
}
