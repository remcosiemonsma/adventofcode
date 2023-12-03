package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {
    private Day3 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day3();
    }

    @Test
    void part1Case1() {
        String data = """
                      467..114..
                      ...*......
                      ..35..633.
                      ......#...
                      617*......
                      .....+.58.
                      ..592.....
                      ......755.
                      ...$.*....
                      .664.598..
                      """;

        assertEquals(4361, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(549908, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      467..114..
                      ...*......
                      ..35..633.
                      ......#...
                      617*......
                      .....+.58.
                      ..592.....
                      ......755.
                      ...$.*....
                      .664.598..
                      """;

        assertEquals(467835, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(81166799, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))));
    }
}