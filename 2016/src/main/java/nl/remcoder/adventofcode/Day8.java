package nl.remcoder.adventofcode;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.drawing.Screen;

public class Day8 implements BiAdventOfCodeSolution<Integer, String> {
    private static final Pattern RECT_PATTERN = Pattern.compile("rect (\\d+)x(\\d+)");
    private static final Pattern ROW_PATTERN = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
    private static final Pattern COLUMN_PATTERN = Pattern.compile("rotate column x=(\\d+) by (\\d+)");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var screen = new Screen(50, 6);

        input.map(this::transformLineToOperation)
             .forEach(operation -> operation.performOperation(screen));

        return screen.countPixels();
    }

    @Override
    public String handlePart2(Stream<String> input) {
        var screen = new Screen(50, 6);

        input.map(this::transformLineToOperation)
             .forEach(operation -> operation.performOperation(screen));

        return screen.readScreen();
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
        void performOperation(Screen screen);
    }

    private static class RectOperation implements Operation {
        private final int width;
        private final int height;

        private RectOperation(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void performOperation(Screen screen) {
            for (var y = 0; y < height; y++) {
                for (var x = 0; x < width; x++) {
                    screen.drawPixel(x, y);
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
        public void performOperation(Screen screen) {
            boolean[] row = screen.getData()[rowNumber];

            screen.getData()[rowNumber] = rotateArray(row, distance);
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
        public void performOperation(Screen screen) {
            var column = new boolean[screen.getHeight()];

            for (var linenumber = 0; linenumber < screen.getHeight(); linenumber++) {
                column[linenumber] = screen.getData()[linenumber][columnNumber];
            }

            column = rotateArray(column, distance);

            for (var linenumber = 0; linenumber < screen.getHeight(); linenumber++) {
                screen.getData()[linenumber][columnNumber] = column[linenumber];
            }
        }
    }

    private static boolean[] rotateArray(boolean[] row, int distance) {
        var newRow = new boolean[row.length];

        System.arraycopy(row, 0, newRow, distance, row.length - distance);
        System.arraycopy(row, row.length - distance, newRow, 0, distance);

        return newRow;
    }
}
