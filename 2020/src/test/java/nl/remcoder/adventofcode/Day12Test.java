package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    private Day12 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day12();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       F10
                       N3
                       F7
                       R90
                       F11
                       """;

        assertEquals(25, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(1319, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2020/day12/input"))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       F10
                       N3
                       F7
                       R90
                       F11
                       """;

        assertEquals(286, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(62434, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2020/day12/input"))));
    }
}