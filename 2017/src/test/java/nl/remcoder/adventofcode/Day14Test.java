package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    private Day14 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day14();
    }

    @Test
    void part1Case1() {
        String data = """
                      flqrgnkx
                      """;

        assertEquals(8108, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(8140, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day14/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      flqrgnkx
                      """;

        assertEquals(1242, testSubject.handlePart2(data.lines()));
    }
    @Test
    void testPart2Input() throws Exception {
        assertEquals(1182, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2017/day14/input"))));
    }
}