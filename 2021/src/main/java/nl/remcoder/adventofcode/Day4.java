package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
    public int handlePart1(Stream<String> input) {
        List<String> lines = input.collect(Collectors.toList());

        int[] numbers = Arrays.stream(lines.get(0).split(","))
                              .mapToInt(Integer::parseInt)
                              .toArray();

        List<int[][]> boards = new ArrayList<>();

        for (int i = 2; i < lines.size(); i += 6) {
            int[][] board = new int[5][];

            for (int j = 0; j < 5; j++) {
                board[j] = Arrays.stream(lines.get(i + j).trim().split(" +"))
                                 .mapToInt(Integer::parseInt)
                                 .toArray();
            }

            boards.add(board);
        }

        int position = 0;

        while (boards.stream().noneMatch(this::isBoardWinner)) {
            int number = numbers[position++];
            boards.forEach(board -> this.drawNumber(board, number));
        }

        int lastnumber = numbers[position - 1];

        return boards.stream()
                     .filter(this::isBoardWinner)
                     .findFirst()
                     .map(board -> this.calculateScore(board, lastnumber))
                     .get();
    }

    public int handlePart2(Stream<String> input) {
        List<String> lines = input.collect(Collectors.toList());

        int[] numbers = Arrays.stream(lines.get(0).split(","))
                              .mapToInt(Integer::parseInt)
                              .toArray();

        List<int[][]> boards = new ArrayList<>();

        for (int i = 2; i < lines.size(); i += 6) {
            int[][] board = new int[5][];

            for (int j = 0; j < 5; j++) {
                board[j] = Arrays.stream(lines.get(i + j).trim().split(" +"))
                                 .mapToInt(Integer::parseInt)
                                 .toArray();
            }

            boards.add(board);
        }

        int position = 0;

        while (boards.size() > 1) {
            int number = numbers[position++];

            boards.forEach(board -> drawNumber(board, number));

            boards.removeIf(this::isBoardWinner);
        }

        int[][] lastBoard = boards.get(0);

        while(!isBoardWinner(lastBoard)) {
            int number = numbers[position++];

            drawNumber(lastBoard, number);
        }

        return calculateScore(lastBoard, numbers[position - 1]);
    }

    private int calculateScore(int[][] board, int lastnumber) {
        int sum = 0;

        for (int[] line : board) {
            for (int number : line) {
                if (number != -1) {
                    sum += number;
                }
            }
        }

        return sum * lastnumber;
    }

    private boolean isBoardWinner(int[][] board) {
        if (Arrays.stream(board).anyMatch(line -> Arrays.stream(line).allMatch(i -> i == -1))) {
            return true;
        }
        for (int i = 0; i < board.length; i++) {
            if (board[0][i] == -1 && board[1][i] == -1 && board[2][i] == -1 && board[3][i] == -1 && board[4][i] == -1) {
                return true;
            }
        }

        return false;
    }

    private void drawNumber(int[][] board, int number) {
        for (int[] line : board) {
            for (int i = 0; i < line.length; i++) {
                if (line[i] == number) {
                    line[i] = -1;
                }
            }
        }
    }
}
