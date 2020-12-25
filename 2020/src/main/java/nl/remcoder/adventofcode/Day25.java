package nl.remcoder.adventofcode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day25 {
    public long handlePart1(Stream<String> input) {
        List<Integer> keys = input.map(Integer::parseInt).collect(Collectors.toList());

        int loopCount1 = determineLoopCount(keys.get(0));
        int loopCount2 = determineLoopCount(keys.get(1));

        System.out.println(loopCount1);
        System.out.println(loopCount2);

        long key = calculateKey(keys.get(0), loopCount2);
        long checkKey = calculateKey(keys.get(1), loopCount1);

        System.out.println(key);
        System.out.println(checkKey);

        System.out.println(key == checkKey);

        return key;
    }

    private long calculateKey(int subjectNumber, int loopCount) {
        long value = 1;

        for (int loop = 0; loop < loopCount; loop++) {
            value *= subjectNumber;
            value %= 20201227;
        }

        return value;
    }

    private int determineLoopCount(int key) {
        int loopCount = 0;
        int subjectNumber = 7;

        int value = 1;

        while (value != key) {
            loopCount++;

            value *= subjectNumber;
            value %= 20201227;
        }

        return loopCount;
    }
}
