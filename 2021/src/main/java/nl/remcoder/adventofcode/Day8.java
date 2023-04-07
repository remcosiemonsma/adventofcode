package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day8 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return input.map(s -> s.split(" \\| "))
                    .map(s -> s[1])
                    .map(s -> s.split(" "))
                    .flatMap(Arrays::stream)
                    .filter(s -> s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7)
                    .count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return input.mapToLong(this::determineValue)
                    .sum();
    }

    public int determineValue(String s) {
        var split = s.split(" \\| ");

        var one = findOne(split[0]);
        var four = findFour(split[0]);
        var seven = findSeven(split[0]);
        var eight = findEight(split[0]);
        var three = findThree(split[0], seven);
        var six = findSix(split[0], one);
        var nine = findNine(split[0], three);
        var zero = findZero(split[0], six, nine);
        var five = findFive(split[0], three, nine);
        var two = findTwo(split[0], three, five);

        var sb = new StringBuilder();

        for (var segment : split[1].split(" ")) {
            sb.append(determineChar(segment, zero, one, two, three, four, five, six, seven, eight, nine));
        }

        return Integer.parseInt(sb.toString());
    }

    public String findZero(String line, String six, String nine) {
        return Arrays.stream(line.split(" "))
                     .filter(s -> s.length() == 6)
                     .map(this::sortChars)
                     .filter(s -> !(s.equals(six) || s.equals(nine)))
                     .findFirst()
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public String findOne(String line) {
        return Arrays.stream(line.split(" "))
                     .filter(s -> s.length() == 2)
                     .findFirst()
                     .map(this::sortChars)
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public String findTwo(String line, String three, String five) {
        return Arrays.stream(line.split(" "))
                     .filter(s -> s.length() == 5)
                     .map(this::sortChars)
                     .filter(s -> !(s.equals(three) || s.equals(five)))
                     .findFirst()
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public String findThree(String line, String seven) {
        return Arrays.stream(line.split(" "))
                     .filter(s -> s.length() == 5)
                     .filter(s -> s.chars().anyMatch(c -> c == seven.charAt(0)) &&
                                  s.chars().anyMatch(c -> c == seven.charAt(1)) &&
                                  s.chars().anyMatch(c -> c == seven.charAt(2)))
                     .findFirst()
                     .map(this::sortChars)
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public String findFour(String line) {
        return Arrays.stream(line.split(" "))
                     .filter(s -> s.length() == 4)
                     .findFirst()
                     .map(this::sortChars)
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public String findFive(String line, String three, String nine) {
        char missingchar = Stream.of('a', 'b', 'c', 'd', 'e', 'f', 'g')
                                 .filter(c -> !nine.contains(String.valueOf(c)))
                                 .findFirst()
                                 .orElseThrow(() -> new AssertionError("Eek!"));

        return Arrays.stream(line.split(" "))
                     .filter(s -> s.length() == 5)
                     .map(this::sortChars)
                     .filter(s -> !s.equals(three))
                     .filter(s -> !s.contains(String.valueOf(missingchar)))
                     .findFirst()
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public String findSix(String line, String one) {
        return Arrays.stream(line.split(" "))
                     .filter(s -> s.length() == 6)
                     .filter(s -> s.chars().anyMatch(c -> c == one.charAt(0)) ^
                                  s.chars().anyMatch(c -> c == one.charAt(1)))
                     .findFirst()
                     .map(this::sortChars)
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public String findSeven(String line) {
        return Arrays.stream(line.split(" "))
                     .filter(s -> s.length() == 3)
                     .findFirst()
                     .map(this::sortChars)
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public String findEight(String line) {
        return Arrays.stream(line.split(" "))
                     .filter(s -> s.length() == 7)
                     .findFirst()
                     .map(this::sortChars)
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public String findNine(String line, String three) {
        return Arrays.stream(line.split(" "))
                     .filter(s -> s.length() == 6)
                     .filter(s -> s.chars().anyMatch(c -> c == three.charAt(0)) &&
                                  s.chars().anyMatch(c -> c == three.charAt(1)) &&
                                  s.chars().anyMatch(c -> c == three.charAt(2)) &&
                                  s.chars().anyMatch(c -> c == three.charAt(3)) &&
                                  s.chars().anyMatch(c -> c == three.charAt(4)))
                     .findFirst()
                     .map(this::sortChars)
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private String sortChars(String s) {
        var chars = s.toCharArray();

        Arrays.sort(chars);

        return new String(chars);
    }

    private char determineChar(String s, String zero, String one, String two, String three, String four, String five,
                               String six, String seven, String eight, String nine) {
        char c;

        var sorted = sortChars(s);

        if (sorted.equals(zero)) {
            c = '0';
        } else if (sorted.equals(one)) {
            c = '1';
        } else if (sorted.equals(two)) {
            c = '2';
        } else if (sorted.equals(three)) {
            c = '3';
        } else if (sorted.equals(four)) {
            c = '4';
        } else if (sorted.equals(five)) {
            c = '5';
        } else if (sorted.equals(six)) {
            c = '6';
        } else if (sorted.equals(seven)) {
            c = '7';
        } else if (sorted.equals(eight)) {
            c = '8';
        } else if (sorted.equals(nine)) {
            c = '9';
        } else {
            throw new AssertionError("Eek! " + sorted);
        }

        return c;
    }
}
