package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day17 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var steps = input.findFirst().map(Integer::parseInt).orElseThrow(() -> new AssertionError("Eek!"));

        var buffer = new int[] {0, 1};

        var insertionValue = 2;

        var previousPosition = 1;

        for (var i = 0; i < 2016; i++) {
            var position = previousPosition + steps;

            if (position >= buffer.length) {
                position = position % buffer.length;
            }

            var newBuffer = new int[buffer.length + 1];

            System.arraycopy(buffer, 0, newBuffer, 0, position + 1);
            System.arraycopy(buffer, position + 1, newBuffer, position + 2, buffer.length - (position + 1));

            newBuffer[position + 1] = insertionValue;

            buffer = newBuffer;

            previousPosition = position + 1;

            insertionValue++;
        }
        
        return buffer[previousPosition + 1];
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var steps = input.findFirst().map(Integer::parseInt).orElseThrow(() -> new AssertionError("Eek!"));

        var buffer = new int[50000001];
        buffer[0] = 0;
        buffer[1] = 1;

        var insertionValue = 2;

        var previousPosition = 1;

        for (int i = 1; i < 50000000; i++) {
            var position = previousPosition + steps;

            if (position >= i + 1) {
                position = position % (i + 1);
            }

            if (position == 0) {
                var newBuffer = new int[50000001];

                System.arraycopy(buffer, 0, newBuffer, 0, position + 1);
                System.arraycopy(buffer, position + 1, newBuffer, position + 2, (i + 1) - (position + 1));

                newBuffer[position + 1] = insertionValue;

                buffer = newBuffer;
            }

            previousPosition = position + 1;

            insertionValue++;
        }

        return buffer[1];
    }
}
