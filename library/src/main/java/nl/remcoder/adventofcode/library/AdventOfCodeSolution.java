package nl.remcoder.adventofcode.library;

import java.util.stream.Stream;

public interface AdventOfCodeSolution<T> {
    T handlePart1(Stream<String> input);

    T handlePart2(Stream<String> input);
}
