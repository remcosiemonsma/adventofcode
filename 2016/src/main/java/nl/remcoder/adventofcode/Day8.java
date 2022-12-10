package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import nl.remcoder.adventofcode.library.drawing.Character;

public class Day8 {
    private static final Pattern RECT_PATTERN = Pattern.compile("rect (\\d+)x(\\d+)");
    private static final Pattern ROW_PATTERN = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
    private static final Pattern COLUMN_PATTERN = Pattern.compile("rotate column x=(\\d+) by (\\d+)");


    public int handlePart1(Stream<String> input) {
        var screen = new boolean[6][50];

        input.map(this::transformLineToOperation)
             .forEach(operation -> operation.performOperation(screen));

        drawScreen(screen);

        int counter = 0;

        for (var line : screen) {
            for (var pixel : line) {
                if (pixel) {
                    counter++;
                }
            }
        }

        return counter;
    }

    public String handlePart2(Stream<String> input) {
        var screen = new boolean[6][50];

        input.map(this::transformLineToOperation)
             .forEach(operation -> operation.performOperation(screen));

        var chars = new char[10];

        for (var position = 0; position < 10; position++) {
            var character = getCharacter(screen, position);

            chars[position] = matchCaracter(character);
        }

        return new String(chars);
    }

    private char matchCaracter(boolean[][] character) {
        if (Arrays.deepEquals(Character.A, character)) {
            return 'A';
        } else if (Arrays.deepEquals(Character.B, character)) {
            return'B';
        } else if (Arrays.deepEquals(Character.C, character)) {
            return'C';
        } else if (Arrays.deepEquals(Character.D, character)) {
            return'D';
        } else if (Arrays.deepEquals(Character.E, character)) {
            return'E';
        } else if (Arrays.deepEquals(Character.F, character)) {
            return'F';
        } else if (Arrays.deepEquals(Character.G, character)) {
            return'G';
        } else if (Arrays.deepEquals(Character.H, character)) {
            return'H';
        } else if (Arrays.deepEquals(Character.I, character)) {
            return'I';
        } else if (Arrays.deepEquals(Character.J, character)) {
            return'J';
        } else if (Arrays.deepEquals(Character.K, character)) {
            return'K';
        } else if (Arrays.deepEquals(Character.L, character)) {
            return'L';
        } else if (Arrays.deepEquals(Character.M, character)) {
            return'M';
        } else if (Arrays.deepEquals(Character.N, character)) {
            return'N';
        } else if (Arrays.deepEquals(Character.O, character)) {
            return'O';
        } else if (Arrays.deepEquals(Character.P, character)) {
            return'P';
        } else if (Arrays.deepEquals(Character.Q, character)) {
            return'Q';
        } else if (Arrays.deepEquals(Character.R, character)) {
            return'R';
        } else if (Arrays.deepEquals(Character.S, character)) {
            return'S';
        } else if (Arrays.deepEquals(Character.T, character)) {
            return'T';
        } else if (Arrays.deepEquals(Character.U, character)) {
            return'U';
        } else if (Arrays.deepEquals(Character.V, character)) {
            return'V';
        } else if (Arrays.deepEquals(Character.W, character)) {
            return'W';
        } else if (Arrays.deepEquals(Character.X, character)) {
            return'X';
        } else if (Arrays.deepEquals(Character.Y, character)) {
            return'Y';
        } else if (Arrays.deepEquals(Character.Z, character)) {
            return'Z';
        }
        return 0;
    }

    private boolean[][] getCharacter(boolean[][] screen, int position) {
        var character = new boolean[6][5];

        for (int y = 0; y < 6; y++) {
            System.arraycopy(screen[y], position * 5, character[y], 0, 5);
        }
        return character;
    }

    private Operation transformLineToOperation(String line) {
        Operation operation;
        var rectMatcher = RECT_PATTERN.matcher(line);
        var rowMatcher = ROW_PATTERN.matcher(line);
        var columnMatcher = COLUMN_PATTERN.matcher(line);
        if (rectMatcher.matches()) {
            operation = new RectOperation(Integer.parseInt(rectMatcher.group(1)), 
                                          Integer.parseInt(rectMatcher.group(2)));
        } else if (rowMatcher.matches()) {
            operation = new RotateRowOperation(Integer.parseInt(rowMatcher.group(1)),
                                               Integer.parseInt(rowMatcher.group(2)));
        } else if (columnMatcher.matches()) {
            operation =
                    new RotateColumnOperation(Integer.parseInt(columnMatcher.group(1)),
                                              Integer.parseInt(columnMatcher.group(2)));
        } else {
            throw new AssertionError("Could not match line with any pattern: " + line);
        }
        return operation;
    }

    private interface Operation {
        void performOperation(boolean[][] screen);
    }

    private static class RectOperation implements Operation {
        private final int width;
        private final int height;

        private RectOperation(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void performOperation(boolean[][] screen) {
            for (var y = 0; y < height; y++) {
                for (var x = 0; x < width; x++) {
                    screen[y][x] = true;
                }
            }
        }
    }

    private static class RotateRowOperation implements Operation {
        private final int rowNumber;
        private final int distance;

        private RotateRowOperation(int rowNumber, int distance) {
            this.rowNumber = rowNumber;
            this.distance = distance;
        }

        @Override
        public void performOperation(boolean[][] screen) {
            boolean[] row = screen[rowNumber];

            screen[rowNumber] = rotateArray(row, distance);
        }

    }

    private static class RotateColumnOperation implements Operation {

        private final int columnNumber;

        private final int distance;

        private RotateColumnOperation(int columnNumber, int distance) {
            this.columnNumber = columnNumber;
            this.distance = distance;
        }

        @Override
        public void performOperation(boolean[][] screen) {
            var column = new boolean[screen.length];

            for (var linenumber = 0; linenumber < screen.length; linenumber++) {
                column[linenumber] = screen[linenumber][columnNumber];
            }

            column = rotateArray(column, distance);

            for (var linenumber = 0; linenumber < screen.length; linenumber++) {
                screen[linenumber][columnNumber] = column[linenumber];
            }
        }
    }

    private static boolean[] rotateArray(boolean[] row, int distance) {
        var newRow = new boolean[row.length];

        System.arraycopy(row, 0, newRow, distance, row.length - distance);
        System.arraycopy(row, row.length - distance, newRow, 0, distance);

        return newRow;
    }

    private void drawScreen(boolean[][] screen) {
        for (var line : screen) {
            for (var pixel : line) {
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
}
