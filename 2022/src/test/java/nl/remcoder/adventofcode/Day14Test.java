package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    private Day14 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day14();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       498,4 -> 498,6 -> 496,6
                       503,4 -> 502,4 -> 502,9 -> 494,9
                       """;

        assertEquals(24, testSubject.handlePart1(input.lines()));
    }
    
    @Test
    void testPart1Input() throws Exception {
        assertEquals(828, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day14/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       498,4 -> 498,6 -> 496,6
                       503,4 -> 502,4 -> 502,9 -> 494,9
                       """;

        assertEquals(93, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(25500, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day14/input"))));
    }
}