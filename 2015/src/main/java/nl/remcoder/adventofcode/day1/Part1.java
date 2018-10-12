package nl.remcoder.adventofcode.day1;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI())).get(0);

        int floorcounter = 0;

        char[] chars = line.toCharArray();
        for (char c : chars) {
            if (c == '(') {
                floorcounter++;
            } else if (c == ')') {
                floorcounter--;
            }
        }

        System.out.println(floorcounter);
    }
}
