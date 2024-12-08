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
    void testPart1Case1() {
        String input = """
                       00100
                       11110
                       10110
                       10111
                       10101
                       01111
                       00111
                       11100
                       10000
                       11001
                       00010
                       01010
                       """;

        assertEquals(198, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(3959450, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2021/day3/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       00100
                       11110
                       10110
                       10111
                       10101
                       01111
                       00111
                       11100
                       10000
                       11001
                       00010
                       01010
                       """;

        assertEquals(230, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(7440311, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2021/day3/input"))));
    }
}