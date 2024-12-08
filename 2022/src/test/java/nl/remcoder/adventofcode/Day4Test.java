package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {
    private Day4 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day4();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       2-4,6-8
                       2-3,4-5
                       5-7,7-9
                       2-8,3-7
                       6-6,4-6
                       2-6,4-8
                       """;

        assertEquals(2, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(305, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day4/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       2-4,6-8
                       2-3,4-5
                       5-7,7-9
                       2-8,3-7
                       6-6,4-6
                       2-6,4-8
                       """;

        assertEquals(4, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(811, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day4/input"))));
    }
}