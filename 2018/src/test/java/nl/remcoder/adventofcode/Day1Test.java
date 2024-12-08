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
    void part1Case1() {
        String data = """
                      +1
                      -2
                      +3
                      +1
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }
    
    @Test
    void testPart1Input() throws Exception {
        assertEquals(513, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day1/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      +1
                      -2
                      +3
                      +1
                      """;

        assertEquals(2, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(287, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day1/input"))));
    }
}