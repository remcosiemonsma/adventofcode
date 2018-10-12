package nl.remcoder.adventofcode.day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()));

        int amountToOrder = 0;

        for (String line : lines) {
            String[] dimensions = line.split("x");

            int length = Integer.valueOf(dimensions[0]);
            int width = Integer.valueOf(dimensions[1]);
            int height = Integer.valueOf(dimensions[2]);

            int side1 = length * width;
            int side2 = length * height;
            int side3 = width * height;

            int extra = side1 < side2 && side1 < side3 ? side1 : side2 < side3 ? side2 : side3;

            int total = (2 * side1) + (2 * side2) + (2 * side3) + extra;

            amountToOrder += total;
        }

        System.out.println(amountToOrder);
    }
}
