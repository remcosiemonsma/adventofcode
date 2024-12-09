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
    void part1Case1() {
        String data = """
                      2333133121414131402
                      """;

        assertEquals(1928, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(6341711060162L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day9/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      2333133121414131402
                      """;

        assertEquals(2858, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(6377400869326L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day9/input"))));
    }
}