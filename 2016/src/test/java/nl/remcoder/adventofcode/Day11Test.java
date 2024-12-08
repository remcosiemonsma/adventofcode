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
                      The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
                      The second floor contains a hydrogen generator.
                      The third floor contains a lithium generator.
                      The fourth floor contains nothing relevant.
                      """;

        assertEquals(11, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(31, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day11/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(55, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day11/input"))));
    }
}