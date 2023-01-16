package nl.remcoder.adventofcode.library.drawing;

import nl.remcoder.adventofcode.library.model.Grid;

import java.util.Arrays;
import java.util.Objects;

public class Screen {
    private final boolean[][] screen;

    public Screen(int width, int height) {
        screen = new boolean[height][width];
    }

    public Screen(Grid<Boolean> grid) {
        //this will fail when first character is an I, this character starts with all off values on first column
        var leftMostX = getLeftMostX(grid);
        var topMostY = getTopMostY(grid);
        var rightMostX = getRightMostX(grid);
        var bottomMostY = getBottomMostY(grid);

        var height = bottomMostY - topMostY + 1;
        int width = rightMostX - leftMostX + 1;
        
        if (width % 5 != 0){
            width += 5 - (width % 5);
        }
        
        screen = new boolean[height][width];

        for (var y = topMostY; y <= bottomMostY; y++) {
            for (var x = leftMostX; x <= rightMostX; x++) {
                screen[y - topMostY][x - leftMostX] = Boolean.TRUE.equals(grid.get(x, y));
            }
        }
        
        if (matchCaracter(getCharacter(0)) == '#') {
            //failed to read value, first character might be an I, offset by 1 and try again, if we still fail assume no characters present.
            leftMostX--;
            rightMostX--;
            for (var y = topMostY; y <= bottomMostY; y++) {
                Arrays.fill(screen[y], false);
                for (var x = leftMostX; x <= rightMostX; x++) {
                    screen[y - topMostY][x - leftMostX] = Boolean.TRUE.equals(grid.get(x, y));
                }
            }
        }
    }

    private int getLeftMostX(Grid<Boolean> grid) {
        Integer leftMostX = null;

        var x = 0;
        while (leftMostX == null && x <= grid.getEndx()) {
            for (var y = 0; y <= grid.getEndy(); y++) {
                if (Boolean.TRUE.equals(grid.get(x, y))) {
                    leftMostX = x;
                    break;
                }
            }
            x++;
        }

        return Objects.requireNonNullElse(leftMostX, 0);
    }

    private int getRightMostX(Grid<Boolean> grid) {
        Integer rightMostX = null;

        var x = grid.getEndx();
        while (rightMostX == null && x >= 0) {
            for (var y = 0; y <= grid.getEndy(); y++) {
                if (Boolean.TRUE.equals(grid.get(x, y))) {
                    rightMostX = x;
                    break;
                }
            }
            x--;
        }

        return Objects.requireNonNullElseGet(rightMostX, grid::getEndx);
    }

    private int getTopMostY(Grid<Boolean> grid) {
        Integer topMostY = null;

        var y = 0;

        while (topMostY == null && y <= grid.getEndy()) {
            for (var x = 0; x <= grid.getEndx(); x++) {
                if (Boolean.TRUE.equals(grid.get(x, y))) {
                    topMostY = y;
                    break;
                }
            }
            y++;
        }

        return Objects.requireNonNullElse(topMostY, 0);
    }

    private int getBottomMostY(Grid<Boolean> grid) {
        Integer bottomMostY = null;

        var y = grid.getEndy();
        while (bottomMostY == null && y >= 0) {
            for (var x = 0; x <= grid.getEndx(); x++) {
                if (Boolean.TRUE.equals(grid.get(x, y))) {
                    bottomMostY = y;
                    break;
                }
            }
            y--;
        }

        return Objects.requireNonNullElseGet(bottomMostY, grid::getEndy);
    }

    public void drawPixel(int x, int y) {
        screen[y][x] = true;
    }

    public void drawPixel(int x, int y, boolean value) {
        screen[y][x] = value;
    }

    public String readScreen() {
        StringBuilder stringBuilder = new StringBuilder();

        int totalCharacters = screen[0].length / 5;
        for (var currentCharacter = 0; currentCharacter < totalCharacters; currentCharacter++) {
            var character = getCharacter(currentCharacter);

            stringBuilder.append(matchCaracter(character));
        }

        return stringBuilder.toString();
    }

    public boolean[][] getData() {
        return screen;
    }

    public int getWidth() {
        return screen[0].length;
    }

    public int getHeight() {
        return screen.length;
    }

    public int countPixels() {
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

    public void printScreen() {
        for (boolean[] line : screen) {
            for (boolean pixel : line) {
                if (pixel) {
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.print("\n");
        }
    }

    private boolean[][] getCharacter(int position) {
        var character = new boolean[6][5];

        for (int y = 0; y < 6; y++) {
            System.arraycopy(screen[y], position * 5, character[y], 0, 5);
        }
        return character;
    }

    private char matchCaracter(boolean[][] character) {
        if (Arrays.deepEquals(Character.A, character)) {
            return 'A';
        } else if (Arrays.deepEquals(Character.B, character)) {
            return 'B';
        } else if (Arrays.deepEquals(Character.C, character)) {
            return 'C';
        } else if (Arrays.deepEquals(Character.D, character)) {
            return 'D';
        } else if (Arrays.deepEquals(Character.E, character)) {
            return 'E';
        } else if (Arrays.deepEquals(Character.F, character)) {
            return 'F';
        } else if (Arrays.deepEquals(Character.G, character)) {
            return 'G';
        } else if (Arrays.deepEquals(Character.H, character)) {
            return 'H';
        } else if (Arrays.deepEquals(Character.I, character)) {
            return 'I';
        } else if (Arrays.deepEquals(Character.J, character)) {
            return 'J';
        } else if (Arrays.deepEquals(Character.K, character)) {
            return 'K';
        } else if (Arrays.deepEquals(Character.L, character)) {
            return 'L';
        } else if (Arrays.deepEquals(Character.M, character)) {
            return 'M';
        } else if (Arrays.deepEquals(Character.N, character)) {
            return 'N';
        } else if (Arrays.deepEquals(Character.O, character)) {
            return 'O';
        } else if (Arrays.deepEquals(Character.P, character)) {
            return 'P';
        } else if (Arrays.deepEquals(Character.Q, character)) {
            return 'Q';
        } else if (Arrays.deepEquals(Character.R, character)) {
            return 'R';
        } else if (Arrays.deepEquals(Character.S, character)) {
            return 'S';
        } else if (Arrays.deepEquals(Character.T, character)) {
            return 'T';
        } else if (Arrays.deepEquals(Character.U, character)) {
            return 'U';
        } else if (Arrays.deepEquals(Character.V, character)) {
            return 'V';
        } else if (Arrays.deepEquals(Character.W, character)) {
            return 'W';
        } else if (Arrays.deepEquals(Character.X, character)) {
            return 'X';
        } else if (Arrays.deepEquals(Character.Y, character)) {
            return 'Y';
        } else if (Arrays.deepEquals(Character.Z, character)) {
            return 'Z';
        }
        return '#';
    }
}
