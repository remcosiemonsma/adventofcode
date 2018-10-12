package nl.remcoder.adventofcode.day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()));

        int amountToOrder = 0;

        for (String line : lines) {
            String[] dimensions = line.split("x");

            int length = Integer.valueOf(dimensions[0]);
            int width = Integer.valueOf(dimensions[1]);
            int height = Integer.valueOf(dimensions[2]);

            int[] sides = new int[] {length, width, height};

            Arrays.sort(sides);

            int shortestSide = sides[0];
            int secondShortestSide = sides[1];

            int total = shortestSide + shortestSide + secondShortestSide + secondShortestSide + (length * width * height);

            amountToOrder += total;
        }

        System.out.println(amountToOrder);
    }
}
