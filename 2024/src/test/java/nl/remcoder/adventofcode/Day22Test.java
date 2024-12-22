package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day22Test {
    private Day22 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day22();
    }

    @Test
    void part1Case1() {
        String data = """
                      1
                      10
                      100
                      2024
                      """;

        assertEquals(37327623, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(17005483322L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day22/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      1
                      2
                      3
                      2024
                      """;

        assertEquals(23, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1910, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day22/input"))));
    }
}