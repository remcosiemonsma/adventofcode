package nl.remcoder.adventofcode.day5;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String polymer = Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI())).findFirst().get();

        String previousPolymer = polymer;
        String newPolymer = reducePolymer(polymer);

        while (!previousPolymer.equals(newPolymer)) {
            previousPolymer = newPolymer;
            newPolymer = reducePolymer(previousPolymer);
            System.out.println(newPolymer.length());
        }

        System.out.println(newPolymer);

        System.out.println(newPolymer.length());
    }

    private static String reducePolymer(String polymer) {
        for (int i = 0; i < polymer.length() - 1; i++) {
            char c1 = polymer.charAt(i);
            char c2 = polymer.charAt(i + 1);

            if (Character.isLowerCase(c1)) {
                if (Character.isUpperCase(c2)) {
                    char c1Upper = Character.toUpperCase(c1);
                    if (c1Upper == c2) {
                        StringBuilder sb = new StringBuilder(polymer);

                        sb.delete(i, i + 2);

                        return sb.toString();
                    }
                }
            } else {
                if (Character.isLowerCase(c2)) {
                    char c1Lower = Character.toLowerCase(c1);
                    if (c1Lower == c2) {
                        StringBuilder sb = new StringBuilder(polymer);

                        sb.delete(i, i + 2);

                        return sb.toString();
                    }
                }
            }
        }

        return polymer;
    }
}
