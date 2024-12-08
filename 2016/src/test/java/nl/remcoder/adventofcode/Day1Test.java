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
        String data = "R2, L3";

        assertEquals(5, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = "R2, R2, R2";

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = "R5, L5, R5, R3";

        assertEquals(12, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(239, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day1/input"))));
    }

    @Test
    void part2Case1() {
        String data = "R8, R4, R4, R8";

        assertEquals(4, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(141, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day1/input"))));
    }
}