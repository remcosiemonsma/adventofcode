package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {
    private Day6 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day6();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       3,4,3,1,2
                       """;

        assertEquals(5934, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(391888, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2021/day6/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       3,4,3,1,2
                       """;

        assertEquals(26984457539L, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1754597645339L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2021/day6/input"))));
    }
}