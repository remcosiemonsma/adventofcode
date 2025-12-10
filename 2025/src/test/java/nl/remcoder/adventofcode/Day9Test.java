package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day9Test {
    private Day9 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day9();
    }

    @Test
    void part1Case1() {
        String data = """
                      7,1
                      11,1
                      11,7
                      9,7
                      9,5
                      2,5
                      2,3
                      7,3
                      """;

        assertEquals(50, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(4781377701L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2025/day9/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      7,1
                      11,1
                      11,7
                      9,7
                      9,5
                      2,5
                      2,3
                      7,3
                      """;

        assertEquals(24, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1470616992, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2025/day9/input"))));
    }
}