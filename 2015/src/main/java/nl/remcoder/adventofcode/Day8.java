package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day8 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.mapToInt(this::calculateReducedSizeDifference)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.mapToInt(this::calculateExpandedSizeDifference)
                    .sum();
    }

    private int calculateExpandedSizeDifference(String line) {
        var expanded = expandString(line);

        return expanded.length() - line.length();
    }

    private String expandString(String line) {
        var stringBuilder = new StringBuilder("\"");

        for (var c : line.toCharArray()) {
            if (c != '"' && c != '\\') {
                stringBuilder.append(c);
            } else {
                stringBuilder.append('\\');
                stringBuilder.append(c);
            }
        }

        stringBuilder.append('"');

        return stringBuilder.toString();
    }

    private int calculateReducedSizeDifference(String line) {
        var reduced = reduceString(line);

        return line.length() - reduced.length();
    }

    private String reduceString(String line) {
        var stringBuilder = new StringBuilder();

        for (int position = 0; position < line.length(); position++) {
            var c = line.charAt(position);
            if (c != '"' && c != '\\') {
                stringBuilder.append(c);
            } else if (c == '\\') {
                var next = line.charAt(++position);
                if (next == 'x') {
                    var first = line.charAt(++position);
                    var second = line.charAt(++position);

                    var value = (char) Integer.parseInt(String.valueOf(new char[]{first, second}), 16);
                    stringBuilder.append(value);
                } else {
                    stringBuilder.append(next);
                }
            }
        }

        return stringBuilder.toString();
    }
}
