package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {
    private Day6 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day6();
    }

    @Test
    void part1Case1() {
        String data = """
                      1, 1
                      1, 6
                      8, 3
                      3, 4
                      5, 5
                      8, 9
                      """;

        assertEquals(17, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(5975, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day6/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      1, 1
                      1, 6
                      8, 3
                      3, 4
                      5, 5
                      8, 9
                      """;

        testSubject.setRequiredValue(32);
        
        assertEquals(16, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setRequiredValue(10000);
        
        assertEquals(38670, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day6/input"))));
    }
}