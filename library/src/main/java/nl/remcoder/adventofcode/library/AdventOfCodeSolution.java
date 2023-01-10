package nl.remcoder.adventofcode.library;

import java.util.stream.Stream;

public interface AdventOfCodeSolution<T> {
    T handlePart1(Stream<String> input) throws Exception;

    T handlePart2(Stream<String> input) throws Exception;
}
