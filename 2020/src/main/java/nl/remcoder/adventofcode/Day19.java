package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day19 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var rules = new HashMap<Integer, String>();
        var lines = new ArrayList<String>();

        input.filter(s -> !s.isBlank())
             .forEach(line -> {
                 if (line.charAt(0) == 'a' || line.charAt(0) == 'b') {
                     lines.add(line);
                 } else {
                     String[] split = line.split(": ");
                     rules.put(Integer.parseInt(split[0]), split[1]);
                 }
             });

        var pattern = "^" + buildPattern(rules.get(0), rules) + "$";

        var regex = Pattern.compile(pattern);

        return lines.stream()
                    .filter(line -> regex.matcher(line).matches())
                    .count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var rules = new HashMap<Integer, String>();
        var lines = new ArrayList<String>();

        input.filter(s -> !s.isBlank())
             .forEach(line -> {
                 if (line.charAt(0) == 'a' || line.charAt(0) == 'b') {
                     lines.add(line);
                 } else {
                     String[] split = line.split(": ");
                     rules.put(Integer.parseInt(split[0]), split[1]);
                 }
             });

        rules.put(8, "42 +");
        rules.put(11, "42 31 | 42 11 31");

        var pattern = "^" + buildPatternWithMaxRecursionDepth(rules.get(0), rules, new HashMap<>()) + "$";

        var regex = Pattern.compile(pattern);

        return lines.stream()
                    .filter(line -> regex.matcher(line).matches())
                    .count();
    }

    private String buildPatternWithMaxRecursionDepth(String string, Map<Integer, String> rules, Map<Integer, Integer> rulesSeen) {
        if (string.charAt(0) == '"') {
            return String.valueOf(string.charAt(1));
        }

        var patternBuilder = new StringBuilder("(");

        for (var split : string.split(" ")) {
            if (split.charAt(0) == '|') {
                patternBuilder.append('|');
            } else if (split.charAt(0) == '+') {
                patternBuilder.append('+');
            } else {
                var ruleId = Integer.parseInt(split);
                var amountTimesSeen = rulesSeen.compute(ruleId, (key, value) -> {
                    if (value == null) {
                        return 1;
                    } else {
                        return value + 1;
                    }
                });
                if (amountTimesSeen <= 10) {
                    patternBuilder.append(buildPatternWithMaxRecursionDepth(rules.get(ruleId), rules, rulesSeen));
                }
                rulesSeen.remove(ruleId);
            }
        }

        patternBuilder.append(")");

        return patternBuilder.toString();
    }

    private String buildPattern(String string, Map<Integer, String> rules) {
        if (string.charAt(0) == '"') {
            return String.valueOf(string.charAt(1));
        }

        var patternBuilder = new StringBuilder("(");

        for (var split : string.split(" ")) {
            if (split.charAt(0) == '|') {
                patternBuilder.append('|');
            } else {
                patternBuilder.append(buildPattern(rules.get(Integer.parseInt(split)), rules));
            }
        }

        patternBuilder.append(")");
        return patternBuilder.toString();
    }
}
