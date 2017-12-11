package nl.remcoder.adventofcode.day9;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day9/input").toURI())).get(0);

        int nesting = 0;
        boolean skipNext = false;
        boolean garbage = false;
        int totalScore = 0;

        for (char c : line.toCharArray()) {
            if (!skipNext) {
                switch (c) {
                    case '{':
                        if (!garbage) {
                            nesting++;
                        }
                        break;
                    case '}':
                        if (!garbage) {
                            totalScore += nesting;
                            nesting--;
                        }
                        break;
                    case '<':
                        garbage = true;
                        break;
                    case '>':
                        garbage = false;
                        break;
                    case '!':
                        skipNext = true;
                        break;
                }
            } else {
                skipNext = false;
            }
        }

        System.out.println(totalScore);
    }
}
