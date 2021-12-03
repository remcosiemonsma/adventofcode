package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {
    public int handlePart1(Stream<String> input) {
        List<String> lines = input.collect(Collectors.toList());

        int[] amountones = new int[lines.get(0).length()];

        lines.stream()
             .map(String::toCharArray)
             .forEach(s -> {
                 for (int i = 0; i < s.length; i++) {
                     if (s[i] == '1') {
                         amountones[i] += 1;
                     }
                 }
             });

        int totalelements = lines.size();

        char[] mostcommon = new char[amountones.length];
        char[] leastcommon = new char[amountones.length];

        for (int i = 0; i < amountones.length; i++) {
            if (amountones[i] > totalelements / 2) {
                mostcommon[i] = '1';
                leastcommon[i] = '0';
            } else {
                mostcommon[i] = '0';
                leastcommon[i] = '1';
            }
        }

        int gammarate = Integer.parseInt(new String(mostcommon), 2);
        int epsilonrate = Integer.parseInt(new String(leastcommon), 2);

        return gammarate * epsilonrate;
    }

    public int handlePart2(Stream<String> input) {
        List<String> lines = input.collect(Collectors.toList());

        List<String> possibleOxygen = new ArrayList<>(lines);
        List<String> possibleco2 = new ArrayList<>(lines);

        int position = 0;

        while (possibleOxygen.size() > 1) {
            int temp = position++;

            long amountone = possibleOxygen.stream()
                                           .map(s -> s.charAt(temp))
                                           .filter(c -> c == '1')
                                           .count();

            if (amountone < (double)possibleOxygen.size() / 2) {
                possibleOxygen.removeIf(s -> s.charAt(temp) == '1');
            } else {
                possibleOxygen.removeIf(s -> s.charAt(temp) == '0');
            }
        }

        position = 0;

        while (possibleco2.size() > 1) {
            int temp = position++;

            long amountone = possibleco2.stream()
                                        .map(s -> s.charAt(temp))
                                        .filter(c -> c == '1')
                                        .count();

            if (amountone < (double)possibleco2.size() / 2) {
                possibleco2.removeIf(s -> s.charAt(temp) == '0');
            } else {
                possibleco2.removeIf(s -> s.charAt(temp) == '1');
            }
        }

        int oxygen = Integer.parseInt(possibleOxygen.get(0), 2);
        int co2 = Integer.parseInt(possibleco2.get(0), 2);

        return oxygen * co2;
    }
}
