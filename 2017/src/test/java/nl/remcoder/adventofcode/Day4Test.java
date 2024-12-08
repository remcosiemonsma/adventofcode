package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {
    private Day4 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day4();
    }

    @Test
    void part1Case1() {
        String data = """
                      aa bb cc dd ee
                      aa bb cc dd aa
                      aa bb cc dd aaa
                      """;

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(455, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day4/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      abcde fghij
                      abcde xyz ecdab
                      a ab abc abd abf abj
                      iiii oiii ooii oooi oooo
                      oiii ioii iioi iiio
                      """;

        assertEquals(3, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(186, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2017/day4/input"))));
    }
}