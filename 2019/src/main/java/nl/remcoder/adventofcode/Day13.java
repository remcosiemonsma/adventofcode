package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.ConsumingQueue;
import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.intcodecomputer.OutputConsumer;
import nl.remcoder.adventofcode.intcodecomputer.ProducingQueue;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

public class Day13 {
    private int[][] grid;
    private int score = 0;

    public int handlePart1(Stream<String> inputStream) {
        String line = inputStream.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        grid = new int[44][44];

        BlockingQueue<Long> output = new ConsumingQueue(new OutputHandler());

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, null, output);

        intCodeComputer.runProgram();

        return countAmountOfTiles(2);
    }

    public int handlePart2(Stream<String> inputStream) {
        String line = inputStream.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        opcodes[0] = 2;

        grid = new int[44][44];

        BlockingQueue<Long> output = new ConsumingQueue(new OutputHandler());
        BlockingQueue<Long> input = new ProducingQueue(() -> {
            int paddleX = findPaddleX();
            int ballX = findBallX();
            return Integer.compare(ballX, paddleX);
        });

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, input, output);

        intCodeComputer.runProgram();

        return score;
    }

    private int findPaddleX() {
        for (int[] line : grid) {
            for (int i = 0; i < line.length; i++) {
                int pixel = line[i];
                if (pixel == 3) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int findBallX() {
        for (int[] line : grid) {
            for (int i = 0; i < line.length; i++) {
                int pixel = line[i];
                if (pixel == 4) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void printGrid() {
        System.out.println(String.format("Score: %d", score));
        for (int[] line : grid) {
            for (int pixel : line) {
                switch (pixel) {
                    case 0 -> System.out.print(' ');
                    case 1 -> System.out.print('|');
                    case 2 -> System.out.print('-');
                    case 3 -> System.out.print('_');
                    case 4 -> System.out.print('o');
                }
            }
            System.out.println();
        }
    }

    private int countAmountOfTiles(int tileId) {
        int amount = 0;

        for (int[] line : grid) {
            for (int pixel : line) {
                if (pixel == tileId) {
                    amount++;
                }
            }
        }

        return amount;
    }

    private class OutputHandler implements OutputConsumer {
        private State state = State.X;

        private long xValue;
        private long yValue;
        private long idValue;

        @Override
        public void consumeLongValue(Long outputValue) {
            switch (state) {
                case X -> {
                    xValue = outputValue;
                    state = State.Y;
                }
                case Y -> {
                    yValue = outputValue;
                    state = State.ID;
                }
                case ID -> {
                    idValue = outputValue;
                    performPaintOperation((int) xValue, (int) yValue, (int) idValue);
                    state = State.X;
                }
            }
        }
    }

    private void performPaintOperation(int x, int y, int id) {
        if (x == -1 && y == 0) {
            score = id;
        } else {
            grid[y][x] = id;
        }
    }

    private enum State {
        X,
        Y,
        ID
    }
}
