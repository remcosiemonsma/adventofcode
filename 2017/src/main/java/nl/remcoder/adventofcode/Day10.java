package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.hash.KnotHash;
import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day10 implements BiAdventOfCodeSolution<Integer, String> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var values = input.map(s -> s.split(","))
                          .flatMap(Arrays::stream)
                          .mapToInt(Integer::parseInt)
                          .toArray();

        var hashList = IntStream.range(0, 256).toArray();

        KnotHash.performHash(values, hashList, 1);

        return hashList[0] * hashList[1];
    }

    @Override
    public String handlePart2(Stream<String> input) {
        var values = input.findFirst().orElseThrow(() -> new AssertionError("Eek!")).chars().toArray();

        return KnotHash.getHash(values);
    }
}
