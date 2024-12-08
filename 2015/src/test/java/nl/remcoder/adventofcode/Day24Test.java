package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    private Day24 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day24();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       1
                       2
                       3
                       4
                       5
                       7
                       8
                       9
                       10
                       11
                       """;

        assertEquals(99, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(11266889531L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day24/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       1
                       2
                       3
                       4
                       5
                       7
                       8
                       9
                       10
                       11
                       """;

        assertEquals(44, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(77387711, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day24/input"))));
    }
}