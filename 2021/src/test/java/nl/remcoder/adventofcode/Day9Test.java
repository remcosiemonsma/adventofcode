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
    void testPart1Case1() {
        String input = """
                       2199943210
                       3987894921
                       9856789892
                       8767896789
                       9899965678
                       """;

        assertEquals(15, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(489, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2021/day9/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       2199943210
                       3987894921
                       9856789892
                       8767896789
                       9899965678
                       """;

        assertEquals(1134, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1056330, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2021/day9/input"))));
    }
}