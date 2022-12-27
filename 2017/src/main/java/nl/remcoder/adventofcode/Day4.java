package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return input.filter(this::isValidPart1).count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return input.filter(this::isValidPart2).count();
    }

    private boolean isValidPart1(String line) {
        var words = line.split(" ");

        var wordSet = new HashSet<>(Arrays.asList(words));

        return wordSet.size() == words.length;
    }

    private boolean isValidPart2(String line) {
        var words = line.split(" ");

        var wordSet = Arrays.stream(words).map(this::alphabetizeString).collect(Collectors.toSet());

        return wordSet.size() == words.length;
    }

    private String alphabetizeString(String input) {
        var chars = input.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
