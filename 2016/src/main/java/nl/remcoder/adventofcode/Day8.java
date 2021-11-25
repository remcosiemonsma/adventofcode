package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day8 {
    private static final Pattern RECT_PATTERN = Pattern.compile("rect (\\d+)x(\\d+)");
    private static final Pattern ROW_PATTERN = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
    private static final Pattern COLUMN_PATTERN = Pattern.compile("rotate column x=(\\d+) by (\\d+)");

    private static final boolean[][] A = new boolean[][]{{false, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, true, true, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false}};

    private static final boolean[][] B = new boolean[][]{{true, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, true, true, false, false}};

    private static final boolean[][] C = new boolean[][]{{false, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, false, false},
                                                         {true, false, false, false, false},
                                                         {true, false, false, true, false},
                                                         {false, true, true, false, false}};

    private static final boolean[][] D = new boolean[][]{{true, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, true, true, false, false}};

    private static final boolean[][] E = new boolean[][]{{true, true, true, true, false},
                                                         {true, false, false, false, false},
                                                         {true, true, true, false, false},
                                                         {true, false, false, false, false},
                                                         {true, false, false, false, false},
                                                         {true, true, true, true, false}};

    private static final boolean[][] F = new boolean[][]{{true, true, true, true, false},
                                                         {true, false, false, false, false},
                                                         {true, true, true, false, false},
                                                         {true, false, false, false, false},
                                                         {true, false, false, false, false},
                                                         {true, false, false, false, false}};

    private static final boolean[][] G = new boolean[][]{{false, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, false, false},
                                                         {true, false, true, true, false},
                                                         {true, false, false, true, false},
                                                         {false, true, true, true, false}};

    private static final boolean[][] H = new boolean[][]{{true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, true, true, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false}};

    private static final boolean[][] I = new boolean[][]{{false, true, true, true, false},
                                                         {false, false, true, false, false},
                                                         {false, false, true, false, false},
                                                         {false, false, true, false, false},
                                                         {false, false, true, false, false},
                                                         {false, true, true, true, false}};

    private static final boolean[][] J = new boolean[][]{{false, false, true, true, false},
                                                         {false, false, false, true, false},
                                                         {false, false, false, true, false},
                                                         {false, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {false, true, true, false, false}};

    private static final boolean[][] K = new boolean[][]{{true, false, false, true, false},
                                                         {true, false, true, false, false},
                                                         {true, true, false, false, false},
                                                         {true, false, true, false, false},
                                                         {true, false, true, false, false},
                                                         {true, false, false, true, false}};

    private static final boolean[][] L = new boolean[][]{{true, false, false, false, false},
                                                         {true, false, false, false, false},
                                                         {true, false, false, false, false},
                                                         {true, false, false, false, false},
                                                         {true, false, false, false, false},
                                                         {true, true, true, true, false}};

    private static final boolean[][] M = new boolean[][]{{true, false, false, false, true},
                                                         {true, true, false, true, true},
                                                         {true, false, true, false, true},
                                                         {true, false, false, false, true},
                                                         {true, false, false, false, true},
                                                         {true, false, false, false, true}};

    private static final boolean[][] N = new boolean[][]{{true, false, false, true, false},
                                                         {true, true, false, true, false},
                                                         {true, true, false, true, false},
                                                         {true, false, true, true, false},
                                                         {true, false, true, true, false},
                                                         {true, false, false, true, false}};

    private static final boolean[][] O = new boolean[][]{{false, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {false, true, true, false, false}};

    private static final boolean[][] P = new boolean[][]{{true, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, true, true, false, false},
                                                         {true, false, false, false, false},
                                                         {true, false, false, false, false}};

    private static final boolean[][] Q = new boolean[][]{{false, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {false, true, true, true, true}};

    private static final boolean[][] R = new boolean[][]{{true, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, true, true, false, false},
                                                         {true, false, true, false, false},
                                                         {true, false, false, true, false}};

    private static final boolean[][] S = new boolean[][]{{false, true, true, true, false},
                                                         {true, false, false, false, false},
                                                         {true, false, false, false, false},
                                                         {false, true, true, false, false},
                                                         {false, false, false, true, false},
                                                         {true, true, true, false, false}};

    private static final boolean[][] T = new boolean[][]{{true, true, true, true, true},
                                                         {false, false, true, false, false},
                                                         {false, false, true, false, false},
                                                         {false, false, true, false, false},
                                                         {false, false, true, false, false},
                                                         {false, false, true, false, false}};

    private static final boolean[][] U = new boolean[][]{{true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {false, true, true, false, false}};


    private static final boolean[][] V = new boolean[][]{{true, false, false, false, true},
                                                         {true, false, false, false, true},
                                                         {false, true, false, true, false},
                                                         {false, true, false, true, false},
                                                         {false, true, false, true, false},
                                                         {false, false, true, false, false}};


    private static final boolean[][] W = new boolean[][]{{true, false, false, false, true},
                                                         {true, false, false, false, true},
                                                         {true, false, false, false, true},
                                                         {true, false, true, false, true},
                                                         {true, true, false, true, true},
                                                         {true, false, true, false, true}};


    private static final boolean[][] X = new boolean[][]{{true, false, false, false, true},
                                                         {false, true, false, true, false},
                                                         {false, false, true, false, false},
                                                         {false, false, true, false, false},
                                                         {false, true, false, true, false},
                                                         {true, false, false, false, true}};


    private static final boolean[][] Y = new boolean[][]{{true, false, false, false, true},
                                                         {true, false, false, false, true},
                                                         {false, true, false, true, false},
                                                         {false, false, true, false, false},
                                                         {false, false, true, false, false},
                                                         {false, false, true, false, true}};


    private static final boolean[][] Z = new boolean[][]{{true, true, true, true, false},
                                                         {false, false, false, true, false},
                                                         {false, false, true, false, false},
                                                         {false, true, false, false, false},
                                                         {true, false, false, false, false},
                                                         {true, true, true, true, false}};

    public int handlePart1(Stream<String> input) {
        boolean[][] screen = new boolean[6][50];

        input.map(this::transformLineToOperation)
             .forEach(operation -> operation.performOperation(screen));

        drawScreen(screen);

        int counter = 0;

        for (boolean[] line : screen) {
            for (boolean pixel : line) {
                if (pixel) {
                    counter++;
                }
            }
        }

        return counter;
    }

    public String handlePart2(Stream<String> input) {
        boolean[][] screen = new boolean[6][50];

        input.map(this::transformLineToOperation)
             .forEach(operation -> operation.performOperation(screen));

        char[] chars = new char[10];

        for (int position = 0; position < 10; position++) {
            boolean[][] character = getCharacter(screen, position);

            chars[position] = matchCaracter(character);
        }

        return new String(chars);
    }

    private char matchCaracter(boolean[][] character) {
        if (Arrays.deepEquals(A, character)) {
            return 'A';
        } else if (Arrays.deepEquals(B, character)) {
            return'B';
        } else if (Arrays.deepEquals(C, character)) {
            return'C';
        } else if (Arrays.deepEquals(D, character)) {
            return'D';
        } else if (Arrays.deepEquals(E, character)) {
            return'E';
        } else if (Arrays.deepEquals(F, character)) {
            return'F';
        } else if (Arrays.deepEquals(G, character)) {
            return'G';
        } else if (Arrays.deepEquals(H, character)) {
            return'H';
        } else if (Arrays.deepEquals(I, character)) {
            return'I';
        } else if (Arrays.deepEquals(J, character)) {
            return'J';
        } else if (Arrays.deepEquals(K, character)) {
            return'K';
        } else if (Arrays.deepEquals(L, character)) {
            return'L';
        } else if (Arrays.deepEquals(M, character)) {
            return'M';
        } else if (Arrays.deepEquals(N, character)) {
            return'N';
        } else if (Arrays.deepEquals(O, character)) {
            return'O';
        } else if (Arrays.deepEquals(P, character)) {
            return'P';
        } else if (Arrays.deepEquals(Q, character)) {
            return'Q';
        } else if (Arrays.deepEquals(R, character)) {
            return'R';
        } else if (Arrays.deepEquals(S, character)) {
            return'S';
        } else if (Arrays.deepEquals(T, character)) {
            return'T';
        } else if (Arrays.deepEquals(U, character)) {
            return'U';
        } else if (Arrays.deepEquals(V, character)) {
            return'V';
        } else if (Arrays.deepEquals(W, character)) {
            return'W';
        } else if (Arrays.deepEquals(X, character)) {
            return'X';
        } else if (Arrays.deepEquals(Y, character)) {
            return'Y';
        } else if (Arrays.deepEquals(Z, character)) {
            return'Z';
        }
        return 0;
    }

    private boolean[][] getCharacter(boolean[][] screen, int position) {
        boolean[][] character = new boolean[6][5];

        for (int y = 0; y < 6; y++) {
            System.arraycopy(screen[y], position * 5, character[y], 0, 5);
        }
        return character;
    }

    private Operation transformLineToOperation(String line) {
        Operation operation;
        Matcher rectMatcher = RECT_PATTERN.matcher(line);
        Matcher rowMatcher = ROW_PATTERN.matcher(line);
        Matcher columnMatcher = COLUMN_PATTERN.matcher(line);
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
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
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
            boolean[] column = new boolean[screen.length];

            for (int linenumber = 0; linenumber < screen.length; linenumber++) {
                column[linenumber] = screen[linenumber][columnNumber];
            }

            column = rotateArray(column, distance);

            for (int linenumber = 0; linenumber < screen.length; linenumber++) {
                screen[linenumber][columnNumber] = column[linenumber];
            }
        }
    }

    private static boolean[] rotateArray(boolean[] row, int distance) {
        boolean[] newRow = new boolean[row.length];

        System.arraycopy(row, 0, newRow, distance, row.length - distance);
        System.arraycopy(row, row.length - distance, newRow, 0, distance);

        return newRow;
    }

    private void drawScreen(boolean[][] screen) {
        for (boolean[] line : screen) {
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
}
