package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day1 {
    public int handlePart1(Stream<String> input) {
        String line = input.findFirst().get();

        int floorcounter = 0;

        char[] chars = line.toCharArray();
        for (char c : chars) {
            if (c == '(') {
                floorcounter++;
            } else if (c == ')') {
                floorcounter--;
            }
        }

        return floorcounter;
    }

    public int handlePart2(Stream<String> input) {
        String line = input.findFirst().get();

        int floorcounter = 0;
        int position = 0;

        char[] chars = line.toCharArray();

        do {
            char c = chars[position++];
            if (c == '(') {
                floorcounter++;
            } else if (c == ')') {
                floorcounter--;
            }
        } while (floorcounter >= 0);

        return position;
    }
}
