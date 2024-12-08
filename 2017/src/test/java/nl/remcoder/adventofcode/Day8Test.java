package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test {
    private Day8 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day8();
    }

    @Test
    void part1Case1() {
        String data = """
                      b inc 5 if a > 1
                      a inc 1 if b < 5
                      c dec -10 if a >= 1
                      c inc -20 if c == 10
                      """;

        assertEquals(1, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(5221, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day8/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      b inc 5 if a > 1
                      a inc 1 if b < 5
                      c dec -10 if a >= 1
                      c inc -20 if c == 10
                      """;

        assertEquals(10, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(7491, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2017/day8/input"))));
    }
}