package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {
    private Day1 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day1();
    }

    @Test
    void part1Case1() {
        String data = """
                      L68
                      L30
                      R48
                      L5
                      R60
                      L55
                      L1
                      L99
                      R14
                      L82
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1180, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2025/day1/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      L68
                      L30
                      R48
                      L5
                      R60
                      L55
                      L1
                      L99
                      R14
                      L82
                      """;

        assertEquals(6, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(6892, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2025/day1/input"))));
    }
}