package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day9Test {
    private Day9 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day9();
    }

    @Test
    void part1Case1() {
        String data = "ADVENT";

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = "A(1x5)BC";

        assertEquals(7, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = "(3x3)XYZ";

        assertEquals(9, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = "A(2x2)BCD(2x2)EFG";

        assertEquals(11, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case5() {
        String data = "(6x1)(1x3)A";

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(123908, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day9/input"))));
    }

    @Test
    void part2Case1() {
        String data = "(3x3)XYZ";

        assertEquals(9, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = "X(8x2)(3x3)ABCY";

        assertEquals(20, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case3() {
        String data = "(27x12)(20x12)(13x14)(7x10)(1x12)A";

        assertEquals(241920, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case4() {
        String data = "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN";

        assertEquals(445, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(10755693147L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day9/input"))));
    }
}