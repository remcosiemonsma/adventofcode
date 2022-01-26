package nl.remcoder.adventofcode.library;

import java.util.stream.Stream;

public class GridFactory {
    public static boolean[][] createBooleanGridFromInput(Stream<String> input) {
        return input.map(GridFactory::createLine)
                    .toArray(boolean[][]::new);
    }

    public static int[][] createNumberedGridFromInput(Stream<String> input) {
        return input.map(s -> s.chars()
                               .map(i -> Character.digit(i, 10))
                               .toArray())
                    .toArray(int[][]::new);
    }

    private static boolean[] createLine(String s) {
        boolean[] line = new boolean[s.length()];
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
