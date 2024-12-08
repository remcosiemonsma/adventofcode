package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day19Test {
    private Day19 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day19();
    }

    @Test
    void part1Case1() {
        String data = """
                      5
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      6
                      """;

        assertEquals(5, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1830117, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day19/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      5
                      """;

        assertEquals(2, testSubject.handlePart2(data.lines()));
    }
    
    @Test
    void testPart2Input() throws Exception {
        assertEquals(1417887, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day19/input"))));
    }
}