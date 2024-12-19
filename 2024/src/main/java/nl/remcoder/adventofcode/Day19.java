package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day19 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var lines = input.toList();

        var regex = buildRegex(lines.getFirst());

        return lines.stream()
                    .skip(2)
                    .map(regex::matcher)
                    .filter(Matcher::matches)
                    .count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var lines = input.toList();

        var towels = Arrays.asList(lines.getFirst().split(", "));

        return lines.stream()
                    .skip(2)
                    .mapToLong(arrangement -> countPossibilities(arrangement, towels, new HashMap<>()))
                    .sum();
    }

    private long countPossibilities(String arrangement, List<String> towels, Map<String, Long> memo) {
        if (memo.containsKey(arrangement)) {
            return memo.get(arrangement);
        }
        var arrangements = 0L;
        for (var towel : towels) {
            if (arrangement.startsWith(towel)) {
                var newArrangement = arrangement.substring(towel.length());
                if (newArrangement.isBlank()) {
                    arrangements += 1;
                } else {
                    arrangements += countPossibilities(newArrangement, towels, memo);
                }
            }
        }

        memo.put(arrangement, arrangements);

        return arrangements;
    }

    private Pattern buildRegex(String line) {
        return Pattern.compile("(" + String.join("|", line.split(", ")) + ")+");
    }
}
