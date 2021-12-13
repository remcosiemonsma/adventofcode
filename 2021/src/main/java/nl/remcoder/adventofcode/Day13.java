package nl.remcoder.adventofcode;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 {
    private static final boolean[][] A = new boolean[][]{{false, true, true, false, false},
                                                         {true, false, false, true, false},
                                                         {true, false, false, true, false},
                                                         {true, true, true, true, false},
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
        List<String> foldInstructions = new ArrayList<>();
        Set<Point> points = new HashSet<>();

        input.filter(Predicate.not(String::isBlank))
             .forEach(s -> {
                 if (s.startsWith("fold along")) {
                     foldInstructions.add(s);
                 } else {
                     String[] split = s.split(",");

                     points.add(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                 }
             });

        switch (foldInstructions.get(0).charAt(11)) {
            case 'x' -> foldVertical(points, Integer.parseInt(foldInstructions.get(0).substring(13)));
            case 'y' -> foldHorizontal(points, Integer.parseInt(foldInstructions.get(0).substring(13)));
        }

        return points.size();
    }

    public String handlePart2(Stream<String> input) {
        List<String> foldInstructions = new ArrayList<>();
        Set<Point> points = new HashSet<>();

        input.filter(Predicate.not(String::isBlank))
             .forEach(s -> {
                 if (s.startsWith("fold along")) {
                     foldInstructions.add(s);
                 } else {
                     String[] split = s.split(",");

                     points.add(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                 }
             });

        foldInstructions.forEach(instruction -> {
            switch (instruction.charAt(11)) {
                case 'x' -> foldVertical(points, Integer.parseInt(instruction.substring(13)));
                case 'y' -> foldHorizontal(points, Integer.parseInt(instruction.substring(13)));
            }
        });

        int highestY = points.stream().mapToInt(Point::y).max().getAsInt();
        int highestX = points.stream().mapToInt(Point::x).max().getAsInt();

        boolean[][] screen = new boolean[highestY + 1][highestX + 2];

        points.forEach(point -> screen[point.y][point.x] = true);

        char[] chars = new char[screen[0].length / 5];

        for (int position = 0; position < screen[0].length / 5; position++) {
            boolean[][] character = getCharacter(screen, position);

            chars[position] = matchCaracter(character);
        }

        return new String(chars);
    }

    private void foldHorizontal(Set<Point> points, int foldY) {
        Set<Point> pointsToRemove = new HashSet<>();
        Set<Point> pointsToAdd = points.stream()
                                       .filter(point -> point.y > foldY)
                                       .peek(pointsToRemove::add)
                                       .map(point -> new Point(point.x, foldY - (point.y - foldY)))
                                       .collect(Collectors.toSet());

        points.removeAll(pointsToRemove);
        points.addAll(pointsToAdd);
    }

    private void foldVertical(Set<Point> points, int foldX) {
        Set<Point> pointsToRemove = new HashSet<>();
        Set<Point> pointsToAdd = points.stream()
                                       .filter(point -> point.x > foldX)
                                       .peek(pointsToRemove::add)
                                       .map(point -> new Point(foldX - (point.x - foldX), point.y))
                                       .collect(Collectors.toSet());

        points.removeAll(pointsToRemove);
        points.addAll(pointsToAdd);
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

    private void printGrid(boolean[][] grid) {
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

    private record Point(int x, int y) {
    }
}
