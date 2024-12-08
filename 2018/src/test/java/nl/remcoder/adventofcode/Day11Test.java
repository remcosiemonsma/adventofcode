package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {
    private Day11 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day11();
    }

    @Test
    void part1Case1() {
        String data = """
                      18
                      """;

        assertArrayEquals(new int[] {33, 45}, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      42
                      """;

        assertArrayEquals(new int[] {21, 61}, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertArrayEquals(new int[] {21, 68}, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day11/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      18
                      """;

        assertArrayEquals(new int[] {90, 269, 16}, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      42
                      """;

        assertArrayEquals(new int[] {232, 251, 12}, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertArrayEquals(new int[] {90, 201, 15}, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day11/input"))));
    }
}