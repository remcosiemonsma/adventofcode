package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day6 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var lines = input.map(s -> s.replaceAll(" +", " ")).toList();

        var times = Arrays.stream(lines.getFirst()
                                       .split(" "))
                          .skip(1)
                          .mapToInt(Integer::parseInt)
                          .toArray();

        var records = Arrays.stream(lines.getLast()
                                         .split(" "))
                            .skip(1)
                            .mapToInt(Integer::parseInt)
                            .toArray();

        return IntStream.range(0, times.length)
                        .mapToLong(i -> countWins(times[i], records[i]))
                        .reduce(1, (left, right) -> left * right);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var lines = input.map(s -> s.replaceAll(" +", "")).toList();

        var raceTime = Long.parseLong(lines.getFirst().split(":")[1]);
        var recordDistance = Long.parseLong(lines.getLast().split(":")[1]);

        return countWins(raceTime, recordDistance);
    }

    private long countWins(long raceDuration, long recordDistance) {
        var lowerHold = findLowerBound(raceDuration, recordDistance);
        var upperHold = findUpperBound(raceDuration, recordDistance);

        return (upperHold - lowerHold) + 1;
    }

    private long getDistance(long raceDuration, long holdDuration) {
        return holdDuration * (raceDuration - holdDuration);
    }

    private long findLowerBound(long raceDuration, long recordDistance) {
        var hold = 0;
        var lowerFound = false;

        while (!lowerFound) {
            hold++;
            lowerFound = doesHoldWin(raceDuration, hold, recordDistance);
        }
        return hold;
    }

    private long findUpperBound(long raceDuration, long recordDistance) {
        var hold = raceDuration;
        var upperFound = false;

        while (!upperFound) {
            hold--;
            upperFound = doesHoldWin(raceDuration, hold, recordDistance);
        }
        return hold;
    }

    private boolean doesHoldWin(long raceDuration, long holdDuration, long recordDistance) {
        return getDistance(raceDuration, holdDuration) > recordDistance;
    }
}
