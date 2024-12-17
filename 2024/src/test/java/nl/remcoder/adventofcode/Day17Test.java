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
                      Register A: 729
                      Register B: 0
                      Register C: 0
                      
                      Program: 0,1,5,4,3,0
                      """;

        assertEquals("4,6,3,5,6,3,5,2,1,0", testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals("7,4,2,5,1,4,6,0,4", testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day17/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      Register A: 2024
                      Register B: 0
                      Register C: 0
                      
                      Program: 0,3,5,4,3,0
                      """;

        assertEquals(117440, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(164278764924605L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day17/input"))));
    }
}