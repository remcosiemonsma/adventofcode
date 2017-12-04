package nl.remcoder.adventofcode.day1;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Part2 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI())).get(0);

        int result = 0;

        char[] chars = line.toCharArray();

        for (int i = 0, j = line.length() / 2; i < line.length(); i++, j++) {
            if (j >= line.length()) {
                j = 0;
            }

            if (chars[i] == chars[j]) {
                result += Integer.parseInt("" + chars[i]);
            }
        }

        System.out.println(result);
    }
}
