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

            for (String word : words) {
                wordSet.add(alphabetizeString(word));
            }

            if (wordSet.size() == words.length) {
                validPassphrases++;
            }
        }

        System.out.println(validPassphrases);
    }

    private static String alphabetizeString(String input) {
        char[] chars = input.toCharArray();

        List<Character> charList = new ArrayList<>();

        for (char c : chars) {
            charList.add(c);
        }

        Collections.sort(charList);

        for (int i = 0; i < charList.size(); i++) {
            chars[i] = charList.get(i);
        }

        return new String(chars);
    }
}
