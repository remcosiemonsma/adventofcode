package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day8 {
    public int handlePart1(Stream<String> input) {
        return input.mapToInt(this::calculateReducedSizeDifference)
                    .sum();
    }

    public int handlePart2(Stream<String> input) {
        return input.mapToInt(this::calculateExpandedSizeDifference)
                    .sum();
    }

    private int calculateExpandedSizeDifference(String line) {
        String expanded = expandString(line);

        return expanded.length() - line.length();
    }

    private String expandString(String line) {
        StringBuilder stringBuilder = new StringBuilder("\"");

        for (char c : line.toCharArray()) {
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
        String reduced = reduceString(line);

        return line.length() - reduced.length();
    }

    private String reduceString(String line) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int position = 0; position < line.length(); position++) {
            char c = line.charAt(position);
            if (c != '"' && c != '\\') {
                stringBuilder.append(c);
            } else if (c == '\\') {
                char next = line.charAt(++position);
                if (next == 'x') {
                    char first = line.charAt(++position);
                    char second = line.charAt(++position);

                    char value = (char) Integer.parseInt(String.valueOf(new char[]{first, second}), 16);
                    stringBuilder.append(value);
                } else {
                    stringBuilder.append(next);
                }
            }
        }

        return stringBuilder.toString();
    }
}
