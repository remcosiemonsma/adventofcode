package nl.remcoder.adventofcode.day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()));

        int result = 0;

        for (String line : lines) {
            int largest = Integer.MIN_VALUE;
            int smallest = Integer.MAX_VALUE;

            String[] numbers = line.split("[\\t]");

            for(String numberstring : numbers) {
                int number = Integer.parseInt(numberstring);

                if (number > largest) {
                    largest = number;
                }
                if (number < smallest) {
                    smallest = number;
                }
            }

            int diff = largest - smallest;

            result += diff;
        }

        System.out.println(result);
    }
}
