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
    void part1Case1() {
        String data = """
                      987654321111111
                      811111111111119
                      234234234234278
                      818181911112111
                      """;

        assertEquals(357, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(17092, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2025/day3/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      987654321111111
                      811111111111119
                      234234234234278
                      818181911112111
                      """;

        assertEquals(3121910778619L, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(170147128753455L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2025/day3/input"))));
    }
}