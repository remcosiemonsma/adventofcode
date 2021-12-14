package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
    public int handlePart1(Stream<String> input) {
        List<Board> boards = parseInput(input);

        return boards.stream()
                     .min(Comparator.comparing(Board::getWinInRound))
                     .map(Board::getWinningScore)
                     .orElse(-1);
    }

    public int handlePart2(Stream<String> input) {
        List<Board> boards = parseInput(input);

        return boards.stream()
                     .max(Comparator.comparing(Board::getWinInRound))
                     .map(Board::getWinningScore)
                     .orElse(-1);
    }

    private List<Board> parseInput(Stream<String> input) {
        List<String> lines = input.toList();

        List<Integer> numbers = Arrays.stream(lines.get(0).split(","))
                                      .map(Integer::parseInt)
                                      .toList();

        List<Board> boards = new ArrayList<>();

        for (int i = 2; i < lines.size(); i += 6) {
            int[][] board = new int[5][];

            for (int j = 0; j < 5; j++) {
                board[j] = Arrays.stream(lines.get(i + j).trim().split(" +"))
                                 .mapToInt(Integer::parseInt)
                                 .toArray();
            }

            boards.add(new Board(numbers, board));
        }
        return boards;
    }

    private static class Board {
        private final int winInRound;
        private final int winningScore;

        public Board(List<Integer> numbers, int[][] board) {
            int round = 0;
            int lastNumber = 0;
            for (; round < numbers.size(); round++) {
                lastNumber = numbers.get(round);
                drawNumber(board, lastNumber);
                if (isBoardWinner(board)) {
                    break;
                }
            }

            winInRound = round;
            winningScore = calculateScore(board, lastNumber);
        }

        public int getWinInRound() {
            return winInRound;
        }

        public int getWinningScore() {
            return winningScore;
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

        private boolean isBoardWinner(int[][] board) {
            if (Arrays.stream(board).anyMatch(line -> Arrays.stream(line).allMatch(i -> i == -1))) {
                return true;
            }
            for (int i = 0; i < board.length; i++) {
                if (board[0][i] == -1 && board[1][i] == -1 && board[2][i] == -1 && board[3][i] == -1 &&
                    board[4][i] == -1) {
                    return true;
                }
            }

            return false;
        }

        private int calculateScore(int[][] board, int lastnumber) {
            return Arrays.stream(board)
                         .flatMapToInt(Arrays::stream)
                         .filter(i -> i != -1)
                         .sum() * lastnumber;
        }
    }
}
