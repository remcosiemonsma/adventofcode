package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.ConsumingQueue;
import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.intcodecomputer.OutputConsumer;
import nl.remcoder.adventofcode.intcodecomputer.ProducingQueue;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

public class Day13 {
    private Grid<Integer> grid;
    private int score = 0;

    public int handlePart1(Stream<String> inputStream) {
        var line = inputStream.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        grid = new Grid<>(0, 0, 0, 0);

        BlockingQueue<Long> output = new ConsumingQueue(new OutputHandler());

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, null, output);

        intCodeComputer.runProgram();

        return (int) grid.countElements(2);
    }

    public int handlePart2(Stream<String> inputStream) {
        String line = inputStream.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        opcodes[0] = 2;

        grid = new Grid<>(0, 0, 0, 0);

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
        return grid.findValue(3).x();
    }

    private int findBallX() {
        return grid.findValue(4).x();
    }

    private class OutputHandler implements OutputConsumer {
        private State state = State.X;

        private long xValue;
        private long yValue;
        private long idValue;

        @Override
        public void consumeLongValue(Long outputValue) {
            state = switch (state) {
                case X -> {
                    xValue = outputValue;
                    yield State.Y;
                }
                case Y -> {
                    yValue = outputValue;
                    yield State.ID;
                }
                case ID -> {
                    idValue = outputValue;
                    performPaintOperation((int) xValue, (int) yValue, (int) idValue);
                    yield State.X;
                }
            };
        }
    }

    private void performPaintOperation(int x, int y, int id) {
        if (x == -1 && y == 0) {
            score = id;
        } else {
            grid.set(new Coordinate(x, y), id);
        }
    }

    private enum State {
        X,
        Y,
        ID
    }
}
