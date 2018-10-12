package nl.remcoder.adventofcode.day5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()));

        int niceStrings = 0;

        for (String line : lines) {
            if (!doesStringFulfillFirstRule(line)) {
                continue;
            }
            if (!doesStringFulfillSecondRule(line)) {
                continue;
            }
            niceStrings++;
        }

        System.out.println(niceStrings);
    }

    private static boolean doesStringFulfillFirstRule(String string) {
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length - 2; i++) {
            String currentchars = new String(new char[] {chars[i], chars[i + 1]});

            for (int j = i + 2; j < chars.length - 1; j++) {
                String otherchars = new String(new char[] {chars[j], chars[j + 1]});

                if (currentchars.equals(otherchars)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean doesStringFulfillSecondRule(String string) {
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length - 2; i++) {
            if (chars[i] == chars[i + 2]) {
                return true;
            }
        }
        return false;
    }
}
