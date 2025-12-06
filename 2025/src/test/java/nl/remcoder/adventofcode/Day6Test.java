package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {
    private Day6 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day6();
    }

    @Test
    void part1Case1() {
        String data = """
                      123 328  51 64
                      45 64  387 23
                      6 98  215 314
                      *   +   *   +
                      """;

        assertEquals(4277556, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(5733696195703L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2025/day6/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      123 328  51 64\s
                       45 64  387 23\s
                        6 98  215 314
                      *   +   *   + \s
                      """;

        assertEquals(3263827, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(10951882745757L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2025/day6/input"))));
    }
}