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
                      0 3 6 9 12 15
                      1 3 6 10 15 21
                      10 13 16 21 30 45
                      """;

        assertEquals(114, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1938731307, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day9/input"))));
    }

    @Test
    void part1Case2() {
        String data = """
                      0 3 6 9 12 15
                      1 3 6 10 15 21
                      10 13 16 21 30 45
                      """;

        assertEquals(2, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(948, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day9/input"))));
    }
}