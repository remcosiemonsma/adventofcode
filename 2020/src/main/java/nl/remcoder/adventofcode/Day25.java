package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.List;
import java.util.stream.Stream;

public class Day25 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        List<Integer> keys = input.map(Integer::parseInt).toList();

        int loopCount = determineLoopCount(keys.get(1));

        return calculateKey(keys.get(0), loopCount);
    }

    @Override
    public Integer handlePart2(Stream<String> input) throws Exception {
        System.out.println("Merry Christmas!");
        return null;
    }

    private int calculateKey(int subjectNumber, int loopCount) {
        var value = 1L;

        for (var loop = 0; loop < loopCount; loop++) {
            value *= subjectNumber;
            value %= 20201227;
        }

        return (int) value;
    }

    private int determineLoopCount(int key) {
        var loopCount = 0;
        var subjectNumber = 7;

        var value = 1;

        while (value != key) {
            loopCount++;

            value *= subjectNumber;
            value %= 20201227;
        }

        return loopCount;
    }
}
