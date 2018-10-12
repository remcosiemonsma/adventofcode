package nl.remcoder.adventofcode.day5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()));

        int niceStrings = 0;

        for (String line : lines) {
            if (line.contains("ab") || line.contains("cd") || line.contains("pq") || line.contains("xy")) {
                continue;
            }
            if (line.chars().filter(value -> value == 'a' || value == 'e' || value == 'o' || value == 'i' || value == 'u').count() < 3) {
                continue;
            }
            char[] chars = line.toCharArray();
            char previouschar = chars[0];
            boolean niceString = false;

            for (int i = 1; i < chars.length; i++) {
                if (chars[i] == previouschar) {
                    niceString = true;
                    break;
                }
                previouschar = chars[i];
            }

            if (niceString) {
                niceStrings++;
            }
        }

        System.out.println(niceStrings);
    }
}
