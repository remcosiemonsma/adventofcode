package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day20Test {
    private Day20 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day20();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       1
                       2
                       -3
                       3
                       -2
                       0
                       4
                       """;

        assertEquals(3, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(13967, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day20/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       1
                       2
                       -3
                       3
                       -2
                       0
                       4
                       """;

        assertEquals(1623178306, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1790365671518L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day20/input"))));
    }
}