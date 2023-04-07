package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day19 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> inputStream) throws InterruptedException {
        var line = inputStream.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var grid = new Grid<Long>(0, 0, 50, 50);

        for (var y = 0; y < 50; y++) {
            for (var x = 0; x < 50; x++) {
                var output = new LinkedBlockingQueue<Long>();
                var input = new LinkedBlockingQueue<Long>();

                input.put((long) x);
                input.put((long) y);

                IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, input, output);

                intCodeComputer.runProgram();

                grid.set(x, y, output.take());
            }
        }

        return grid.countElements(1L);
    }

    @Override
    public Long handlePart2(Stream<String> input) throws InterruptedException {
        var line = input.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        var y = 100;
        var x = findStartX(y, opcodes);
        var endX = findEndX(y, x, opcodes);

        while (endX - x != 100) {
            y++;
            x = findStartX(y, opcodes);
            endX = findEndX(y, x, opcodes);
        }

        var santaFound = false;

        var santaX = 0;
        var santaY = 0;

        while (!santaFound) {
            y++;
            endX = findEndX(y, endX, opcodes);

            if (doesSantaFit(endX, y, opcodes)) {
                santaX = endX - 99;
                santaY = y;
                santaFound = true;
            }
        }

        return (santaX * 10000L) + santaY;
    }

    private boolean doesSantaFit(int x, int y, long[] opcodes) throws InterruptedException {
        var output = new LinkedBlockingQueue<Long>();
        var input = new LinkedBlockingQueue<Long>();

        input.put((long) x - 99);
        input.put((long) y + 99);

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, input, output);

        intCodeComputer.runProgram();

        return output.take() == 1L;
    }

    private int findEndX(int y, int startX, long[] opcodes) throws InterruptedException {
        var endFound = false;

        var x = startX;

        while (!endFound) {
            var output = new LinkedBlockingQueue<Long>();
            var input = new LinkedBlockingQueue<Long>();

            input.put((long) x);
            input.put((long) y);

            IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, input, output);

            intCodeComputer.runProgram();

            if (output.take() == 0L) {
                endFound = true;
                x--;
            } else {
                x++;
            }
        }

        return x;
    }

    private int findStartX(int y, long[] opcodes) throws InterruptedException {
        var startFound = false;

        var x = 0;

        while (!startFound) {
            var output = new LinkedBlockingQueue<Long>();
            var input = new LinkedBlockingQueue<Long>();

            input.put((long) x);
            input.put((long) y);

            IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, input, output);

            intCodeComputer.runProgram();

            if (output.take() == 1L) {
                startFound = true;
            } else {
                x++;
            }
        }

        return x;
    }
}
