package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {
    private Day11 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day11();
    }

    @Test
    void part1Case1() {
        String data = """
                      125 17
                      """;

        assertEquals(55312, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(229043, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day11/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(272673043446478L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day11/input"))));
    }
}