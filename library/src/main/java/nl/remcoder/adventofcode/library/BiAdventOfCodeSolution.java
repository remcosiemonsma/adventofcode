package nl.remcoder.adventofcode.library;

import java.util.stream.Stream;

public interface BiAdventOfCodeSolution<T1, T2> {
    T1 handlePart1(Stream<String> input);

    T2 handlePart2(Stream<String> input);
}
