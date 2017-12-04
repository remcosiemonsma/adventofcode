package nl.remcoder.adventofcode.day1;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI())).get(0);

        char previouschar = line.charAt(line.length() - 1);

        int result = 0;

        for (char c : line.toCharArray()) {
            if (c == previouschar) {
                result += Integer.parseInt("" + c);
            }

            previouschar = c;
        }

        System.out.println(result);
    }
}
