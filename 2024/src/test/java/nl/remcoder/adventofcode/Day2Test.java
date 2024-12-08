package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {
    private Day2 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day2();
    }

    @Test
    void part1Case1() {
        String data = """
                      7 6 4 2 1
                      1 2 7 8 9
                      9 7 6 2 1
                      1 3 2 4 5
                      8 6 4 4 1
                      1 3 6 7 9
                      """;

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(670, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day2/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      7 6 4 2 1
                      1 2 7 8 9
                      9 7 6 2 1
                      1 3 2 4 5
                      8 6 4 4 1
                      1 3 6 7 9
                      """;

        assertEquals(4, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(700, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day2/input"))));
    }
}