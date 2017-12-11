package nl.remcoder.adventofcode.day9;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Part2 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day9/input").toURI())).get(0);

        boolean skipNext = false;
        boolean garbage = false;
        int nonCanceledGarbageChars = 0;

        for (char c : line.toCharArray()) {
            if (!skipNext) {
                switch (c) {
                    case '{':
                        if (garbage) {
                            nonCanceledGarbageChars++;
                        }
                        break;
                    case '}':
                        if (garbage) {
                            nonCanceledGarbageChars++;
                        }
                        break;
                    case '<':
                        if (!garbage) {
                            garbage = true;
                        } else {
                            nonCanceledGarbageChars++;
                        }
                        break;
                    case '>':
                        garbage = false;
                        break;
                    case '!':
                        skipNext = true;
                        break;
                    default:
                        if (garbage) {
                            nonCanceledGarbageChars++;
                        }
                        break;
                }
            } else {
                skipNext = false;
            }
        }

        System.out.println(nonCanceledGarbageChars);
    }
}
