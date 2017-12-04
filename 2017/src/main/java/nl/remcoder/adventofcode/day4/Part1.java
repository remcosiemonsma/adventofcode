package nl.remcoder.adventofcode.day4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()));

        int validPassphrases = 0;

        for (String line : lines) {
            String[] words = line.split(" ");

            Set<String> wordSet = new HashSet<>();
            wordSet.addAll(Arrays.asList(words));

            if (wordSet.size() == words.length) {
                validPassphrases++;
            }
        }

        System.out.println(validPassphrases);
    }
}
