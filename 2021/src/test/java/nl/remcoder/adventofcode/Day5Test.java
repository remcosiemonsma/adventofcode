package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
    private Day5 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day5();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       0,9 -> 5,9
                       8,0 -> 0,8
                       9,4 -> 3,4
                       2,2 -> 2,1
                       7,0 -> 7,4
                       6,4 -> 2,0
                       0,9 -> 2,9
                       3,4 -> 1,4
                       0,0 -> 8,8
                       5,5 -> 8,2
                       """;

        assertEquals(5, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(5145, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2021/day5/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       0,9 -> 5,9
                       8,0 -> 0,8
                       9,4 -> 3,4
                       2,2 -> 2,1
                       7,0 -> 7,4
                       6,4 -> 2,0
                       0,9 -> 2,9
                       3,4 -> 1,4
                       0,0 -> 8,8
                       5,5 -> 8,2
                       """;

        assertEquals(12, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(16518, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2021/day5/input"))));
    }
}