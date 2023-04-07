package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Day3 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();

        var amountones = new int[lines.get(0).length()];

        lines.stream()
             .map(String::toCharArray)
             .forEach(s -> {
                 for (int i = 0; i < s.length; i++) {
                     if (s[i] == '1') {
                         amountones[i] += 1;
                     }
                 }
             });

        var totalelements = lines.size();

        var mostcommon = new char[amountones.length];
        var leastcommon = new char[amountones.length];

        for (var i = 0; i < amountones.length; i++) {
            if (amountones[i] > totalelements / 2) {
                mostcommon[i] = '1';
                leastcommon[i] = '0';
            } else {
                mostcommon[i] = '0';
                leastcommon[i] = '1';
            }
        }

        var gammarate = Integer.parseInt(new String(mostcommon), 2);
        var epsilonrate = Integer.parseInt(new String(leastcommon), 2);

        return gammarate * epsilonrate;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();

        var possibleOxygen = new ArrayList<>(lines);
        var possibleco2 = new ArrayList<>(lines);

        var position = 0;

        while (possibleOxygen.size() > 1) {
            var temp = position++;

            var amountone = possibleOxygen.stream()
                                          .map(s -> s.charAt(temp))
                                          .filter(c -> c == '1')
                                          .count();

            if (amountone < (double) possibleOxygen.size() / 2) {
                possibleOxygen.removeIf(s -> s.charAt(temp) == '1');
            } else {
                possibleOxygen.removeIf(s -> s.charAt(temp) == '0');
            }
        }

        position = 0;

        while (possibleco2.size() > 1) {
            var temp = position++;

            var amountone = possibleco2.stream()
                                       .map(s -> s.charAt(temp))
                                       .filter(c -> c == '1')
                                       .count();

            if (amountone < (double) possibleco2.size() / 2) {
                possibleco2.removeIf(s -> s.charAt(temp) == '0');
            } else {
                possibleco2.removeIf(s -> s.charAt(temp) == '1');
            }
        }

        var oxygen = Integer.parseInt(possibleOxygen.get(0), 2);
        var co2 = Integer.parseInt(possibleco2.get(0), 2);

        return oxygen * co2;
    }
}
