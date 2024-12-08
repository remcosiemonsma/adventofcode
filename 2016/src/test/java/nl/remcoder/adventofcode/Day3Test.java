package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {
    private Day3 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day3();
    }

    @Test
    void part1Case1() {
        String data = "5 10 25";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(869, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day3/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      101 301 501
                      102 302 502
                      103 303 503
                      201 401 601
                      202 402 602
                      203 403 603
                      """;

        assertEquals(6, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1544, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day3/input"))));
    }
}