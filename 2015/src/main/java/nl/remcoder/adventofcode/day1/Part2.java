package nl.remcoder.adventofcode.day1;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Part2 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI())).get(0);

        int floorcounter = 0;
        int position;

        char[] chars = line.toCharArray();
        for (position = 0; position < chars.length; position++) {
            char c = chars[position];
            if (c == '(') {
                floorcounter++;
            } else if (c == ')') {
                floorcounter--;
            }

            if (floorcounter < 0) {
                break;
            }
        }

        System.out.println(position + 1);
    }
}
