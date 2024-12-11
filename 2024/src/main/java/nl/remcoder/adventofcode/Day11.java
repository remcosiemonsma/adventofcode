package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class Day11 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var memo = new HashMap<BlinkKey, Long>();

        return input.findFirst()
                    .stream()
                    .map(s -> s.split(" "))
                    .flatMap(Arrays::stream)
                    .map(Long::parseLong)
                    .mapToLong(stone -> countStonesAfterBlink(stone, 25, memo))
                    .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var memo = new HashMap<BlinkKey, Long>();

        return input.findFirst()
                    .stream()
                    .map(s -> s.split(" "))
                    .flatMap(Arrays::stream)
                    .mapToLong(Long::parseLong)
                    .map(stone -> countStonesAfterBlink(stone, 75, memo))
                    .sum();
    }

    private long countStonesAfterBlink(long stone, int amountOfBlinks, HashMap<BlinkKey, Long> memo) {
        var key = new BlinkKey(stone, amountOfBlinks);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        long result;

        if (amountOfBlinks == 0) {
            result = 1;
        } else if (stone == 0) {
            result = (countStonesAfterBlink(1, amountOfBlinks - 1, memo));
        } else {
            int amountOfDigits = Long.toString(stone).length();
            if (amountOfDigits % 2 == 0) {
                var stonesAfterblink = 0L;
                var middle = amountOfDigits / 2;
                var modulo = (int) Math.pow(10, middle);
                var right = stone % modulo;
                var left = (stone - right) / modulo;
                stonesAfterblink += countStonesAfterBlink(right, amountOfBlinks - 1, memo);
                stonesAfterblink += countStonesAfterBlink(left, amountOfBlinks - 1, memo);
                result = stonesAfterblink;
            } else {
                result = countStonesAfterBlink(stone * 2024, amountOfBlinks - 1, memo);
            }
        }

        memo.put(key, result);

        return result;
    }

    private record BlinkKey(long stone, int amountOfBlinks) {
    }
}
