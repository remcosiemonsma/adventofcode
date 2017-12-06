package nl.remcoder.adventofcode.day4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()));

        int validPassphrases = 0;

        for (String line : lines) {
            String[] words = line.split(" ");

            Set<String> wordSet = new HashSet<>();

            Arrays.stream(words).forEach(s -> wordSet.add(alphabetizeString(s)));


            if (wordSet.size() == words.length) {
                validPassphrases++;
            }
        }

        System.out.println(validPassphrases);
    }

    private static String alphabetizeString(String input) {
        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
