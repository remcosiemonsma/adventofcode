package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {
    private Day10 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day10();
    }

    @Test
    void part1Case1() {
        String data = """
                      [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
                      [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
                      [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
                      """;

        assertEquals(7, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(527, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2025/day10/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
                      [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
                      [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
                      """;

        assertEquals(33, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(19810, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2025/day10/input"))));
    }
}