package nl.remcoder.adventofcode.library;

import nl.remcoder.adventofcode.library.model.Grid;

import java.util.stream.Stream;

public class GridFactory {
    public static Grid<Boolean> createBooleanGridFromInput(Stream<String> input) {
        return new Grid<>(readBooleanInput(input));
    }

    private static Boolean[][] readBooleanInput(Stream<String> input) {
        return input.map(GridFactory::createLine)
                    .toArray(Boolean[][]::new);
    }

    public static int[][] createNumberedGridFromInput(Stream<String> input) {
        return input.map(s -> s.chars()
                               .map(i -> Character.digit(i, 10))
                               .toArray())
                    .toArray(int[][]::new);
    }

    private static Boolean[] createLine(String s) {
        Boolean[] line = new Boolean[s.length()];
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            line[i] = c == '#';
        }
        return line;
    }

    public static void printGrid(boolean[][] grid) {
        for (boolean[] line : grid) {
            for (boolean pixel : line) {
                if (pixel) {
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printGrid(int[][] grid) {
        for (int[] line : grid) {
            for (int pixel : line) {
                System.out.print(pixel);
            }
            System.out.println();
        }
        System.out.println();
    }
}
