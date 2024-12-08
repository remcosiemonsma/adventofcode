package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {
    private Day17 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day17();
    }

    @Test
    void part1Case1() {
        String data = """
                      3
                      """;

        assertEquals(638, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(725, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day17/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(27361412, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2017/day17/input"))));
    }
}