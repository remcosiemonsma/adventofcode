package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day19 {
    public int handlePart1(Stream<String> inputStream) throws InterruptedException {
        String line = inputStream.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        long[][] grid = new long[50][50];

        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                BlockingQueue<Long> output = new LinkedBlockingQueue<>();
                BlockingQueue<Long> input = new LinkedBlockingQueue<>();

                input.put((long) x);
                input.put((long) y);

                IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, input, output);

                intCodeComputer.runProgram();

                grid[y][x] = output.take();
            }
        }

        printGrid(grid);

        return countAffectedPoints(grid);
    }

    public int handlePart2(Stream<String> input) throws InterruptedException {
        String line = input.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        int y = 100;
        int x = findStartX(y, opcodes);
        int endX = findEndX(y, x, opcodes);

        while (endX - x != 100) {
            y++;
            x = findStartX(y, opcodes);
            endX = findEndX(y, x, opcodes);
        }

        boolean santaFound = false;

        int santaX = 0;
        int santaY = 0;

        while (!santaFound) {
            y++;
            endX = findEndX(y, endX, opcodes);

            if (doesSantaFit(endX, y, opcodes)) {
                santaX = endX - 99;
                santaY = y;
                santaFound = true;
            }
        }

        return (santaX * 10000) + santaY;
    }

    private boolean doesSantaFit(int x, int y, long[] opcodes) throws InterruptedException {
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();
        BlockingQueue<Long> input = new LinkedBlockingQueue<>();

        input.put((long) x - 99);
        input.put((long) y + 99);

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, input, output);

        intCodeComputer.runProgram();

        return output.take() == 1L;
    }

    private int findEndX(int y, int startX, long[] opcodes) throws InterruptedException {
        boolean endFound = false;

        int x = startX;

        while (!endFound) {
            BlockingQueue<Long> output = new LinkedBlockingQueue<>();
            BlockingQueue<Long> input = new LinkedBlockingQueue<>();

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
        boolean startFound = false;

        int x = 0;

        while (!startFound) {
            BlockingQueue<Long> output = new LinkedBlockingQueue<>();
            BlockingQueue<Long> input = new LinkedBlockingQueue<>();

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

    private int countAffectedPoints(long[][] grid) {
        int amount = 0;

        for (long[] line : grid) {
            for (long pixel : line) {
                if (pixel == 1) {
                    amount++;
                }
            }
        }

        return amount;
    }

    private void printGrid(long[][] grid) {
        for (long[] line : grid) {
            for (long pixel : line) {
                System.out.print(pixel);
            }
            System.out.println();
        }
    }
}
