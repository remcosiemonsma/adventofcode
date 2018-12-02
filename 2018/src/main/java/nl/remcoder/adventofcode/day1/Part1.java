package nl.remcoder.adventofcode.day1;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Part1 {
    public static void main(String[] args) throws Exception {
        System.out.println(Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))
             .mapToInt(Integer::parseInt)
             .sum());
    }
}
