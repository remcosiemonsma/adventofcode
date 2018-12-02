package nl.remcoder.adventofcode.day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        List<String> strings = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()));

        int amountTwice = 0;
        int amountThee = 0;

        for (String s : strings) {
            if (doesStringContainCharTwice(s)) {
                amountTwice++;
            }
            if (doesStringContainCharThreeTimes(s)) {
                amountThee++;
            }
        }

        System.out.println(amountTwice * amountThee);
    }

    private static boolean doesStringContainCharTwice(String s) {
        int[] charAmounts = new int[26];

        s.chars()
         .map(c -> c - 'a')
         .forEach(c1 -> charAmounts[c1]++);

        return Arrays.stream(charAmounts)
              .anyMatch(amount -> amount == 2);
    }

    private static boolean doesStringContainCharThreeTimes(String s) {
        int[] charAmounts = new int[26];

        s.chars()
         .map(c -> c - 'a')
         .forEach(c1 -> charAmounts[c1]++);

        return Arrays.stream(charAmounts)
                     .anyMatch(amount -> amount == 3);
    }
}
