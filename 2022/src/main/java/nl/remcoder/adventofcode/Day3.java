package nl.remcoder.adventofcode;

import java.util.List;
import java.util.stream.Stream;

public class Day3 {

    public int handlePart1(Stream<String> input) {
        return input.mapToInt(this::mapToPriority)
                    .sum();
    }

    public int handlePart2(Stream<String> input) {
        List<String> lines = input.toList();

        int sum = 0;

        for (int i = 0; i < lines.size(); i += 3) {
            String line1 = lines.get(i);
            String line2 = lines.get(i + 1);
            String line3 = lines.get(i + 2);

            for (char c : line1.toCharArray()) {
                if (line2.contains("" + c) && line3.contains("" + c)) {
                    sum += getPriority(c);
                    break;
                }
            }
        }

        return sum;
    }

    private int mapToPriority(String line) {
        String compartment1 = line.substring(0, line.length() / 2);
        String compartment2 = line.substring(line.length() / 2);

        for (char c : compartment1.toCharArray()) {
            if (compartment2.contains("" + c)) {
                return getPriority(c);
            }
        }

        throw new AssertionError("Eek!");
    }

    private static int getPriority(char c) {
        if (Character.isLowerCase(c)) {
            return c - ('a' - 1);
        } else {
            return c - ('A' - 27);
        }
    }
}
