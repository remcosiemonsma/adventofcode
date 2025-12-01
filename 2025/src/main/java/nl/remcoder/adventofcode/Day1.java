package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Long> {

    @Override
    public Long handlePart1(Stream<String> input) {
        return input.map(this::parseInput)
                    .gather(Gatherer.<Integer, AtomicInteger, Integer>ofSequential(() -> new AtomicInteger(50), Day1::processRotationsPart1))
                    .filter(position -> position == 0)
                    .count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return input.map(this::parseInput)
                    .gather(Gatherer.<Integer, AtomicInteger, Integer>ofSequential(() -> new AtomicInteger(50), Day1::processRotationsPart2))
                    .mapToLong(Integer::intValue)
                    .sum();
    }

    private static boolean processRotationsPart1(AtomicInteger state, Integer element, Gatherer.Downstream<? super Integer> downstream) {
        var position = state.get();

        position += element % 100;

        if (position < 0) {
            position += 100;
        }
        if (position >= 100) {
            position -= 100;
        }

        downstream.push(position);
        state.set(position);
        return true;
    }

    private static boolean processRotationsPart2(AtomicInteger state, Integer rotation, Gatherer.Downstream<? super Integer> downstream) {
        var position = state.get();

        //If we are at 0 we have already counted the current 0 position, and should not add it when we end up below 0 later.
        var addition = state.get() == 0 ? 0 : 1;

        position += rotation;

        var zeropasses = 0;

        if (position == 0) {
            zeropasses++;
        }
        if (position < 0) {
            zeropasses = Math.abs(position / 100) + addition;
            position %= 100;
            if (position < 0) {
                position += 100;
            }
        }
        if (position >= 100) {
            zeropasses = Math.abs(position / 100);
            position %= 100;
        }

        downstream.push(zeropasses);
        state.set(position);
        return true;
    }

    private Integer parseInput(String line) {
        var steps = Integer.parseInt(line.substring(1));

        if (line.charAt(0) == 'L') {
            steps *= -1;
        }

        return steps;
    }
}
