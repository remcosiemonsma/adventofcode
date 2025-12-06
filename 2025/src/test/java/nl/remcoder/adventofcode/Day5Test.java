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
    void part1Case1() {
        String data = """
                      3-5
                      10-14
                      16-20
                      12-18
                
                      1
                      5
                      8
                      11
                      17
                      32
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(848, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2025/day5/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      3-5
                      10-14
                      16-20
                      12-18
                
                      1
                      5
                      8
                      11
                      17
                      32
                      """;

        assertEquals(14, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(334714395325710L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2025/day5/input"))));
    }
}