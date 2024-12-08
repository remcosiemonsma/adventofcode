package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    private Day12 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day12();
    }

    @Test
    void part1Case1() {
        String data = """
                      0 <-> 2
                      1 <-> 1
                      2 <-> 0, 3, 4
                      3 <-> 2, 4
                      4 <-> 2, 3, 6
                      5 <-> 6
                      6 <-> 4, 5
                      """;

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(115, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day12/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      0 <-> 2
                      1 <-> 1
                      2 <-> 0, 3, 4
                      3 <-> 2, 4
                      4 <-> 2, 3, 6
                      5 <-> 6
                      6 <-> 4, 5
                      """;

        assertEquals(2, testSubject.handlePart2(data.lines()));
    }
    @Test
    void testPart2Input() throws Exception {
        assertEquals(221, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2017/day12/input"))));
    }
}