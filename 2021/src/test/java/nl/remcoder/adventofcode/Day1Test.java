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
    void testPart1Case1() {
        String input = """
                       199
                       200
                       208
                       210
                       200
                       207
                       240
                       269
                       260
                       263
                       """;

        assertEquals(7, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1374, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2021/day1/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       199
                       200
                       208
                       210
                       200
                       207
                       240
                       269
                       260
                       263
                       """;

        assertEquals(5, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1418, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2021/day1/input"))));
    }
}