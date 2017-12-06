package nl.remcoder.adventofcode.day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()));

        int result = 0;

        for (String line : lines) {
            String[] numbers = line.split("[\\t]");

            outer: for (int i = 0; i < numbers.length; i++) {
                int numberi = Integer.parseInt(numbers[i]);
                for (int j = 0; j < numbers.length; j++) {
                    if (i == j) {
                        continue;
                    }
                    int numberj = Integer.parseInt(numbers[j]);

                    if (numberi % numberj == 0) {
                        result += numberi / numberj;
                        continue outer;
                    }
                }
            }
        }

        System.out.println(result);
    }
}
