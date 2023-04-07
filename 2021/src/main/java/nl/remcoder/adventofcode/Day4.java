package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Day4 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var boards = parseInput(input);

        return boards.stream()
                     .min(Comparator.comparing(Board::getWinInRound))
                     .map(Board::getWinningScore)
                     .orElse(-1);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var boards = parseInput(input);

        return boards.stream()
                     .max(Comparator.comparing(Board::getWinInRound))
                     .map(Board::getWinningScore)
                     .orElse(-1);
    }

    private List<Board> parseInput(Stream<String> input) {
        var lines = input.toList();

        var numbers = Arrays.stream(lines.get(0).split(","))
                            .map(Integer::parseInt)
                            .toList();

        var boards = new ArrayList<Board>();

        for (int i = 2; i < lines.size(); i += 6) {
            var board = new int[5][];

            for (var j = 0; j < 5; j++) {
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
            var round = 0;
            var lastNumber = 0;
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
            for (var line : board) {
                for (var i = 0; i < line.length; i++) {
                    if (line[i] == number) {
                        line[i] = -1;
                    }
                }
            }
        }

        private boolean isBoardWinner(int[][] board) {
            if (Arrays.stream(board)
                      .anyMatch(line -> Arrays.stream(line)
                                              .allMatch(i -> i == -1))) {
                return true;
            }
            for (var i = 0; i < board.length; i++) {
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
