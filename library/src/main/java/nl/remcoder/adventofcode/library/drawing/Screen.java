package nl.remcoder.adventofcode.library.drawing;

import java.util.Arrays;

public class Screen {
    private final boolean[][] screen;

    public Screen(int width, int height) {
        screen = new boolean[height][width];
    }
    
    public void drawPixel(int x, int y) {
        screen[y][x] = true;
    }
    
    public String readScreen() {
        StringBuilder stringBuilder = new StringBuilder();

        int totalCharacters = screen[0].length / 5;
        for (var currentCharacter = 0; currentCharacter < totalCharacters; currentCharacter++) {
            var character = getCharacter(screen, currentCharacter);

            stringBuilder.append(matchCaracter(character));
        }
        
        return stringBuilder.toString();
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

    private boolean[][] getCharacter(boolean[][] screen, int position) {
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
        return '#';
    }
}
