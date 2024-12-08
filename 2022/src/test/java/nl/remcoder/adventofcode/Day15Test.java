package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {
    private Day15 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day15();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       Sensor at x=2, y=18: closest beacon is at x=-2, y=15
                       Sensor at x=9, y=16: closest beacon is at x=10, y=16
                       Sensor at x=13, y=2: closest beacon is at x=15, y=3
                       Sensor at x=12, y=14: closest beacon is at x=10, y=16
                       Sensor at x=10, y=20: closest beacon is at x=10, y=16
                       Sensor at x=14, y=17: closest beacon is at x=10, y=16
                       Sensor at x=8, y=7: closest beacon is at x=2, y=10
                       Sensor at x=2, y=0: closest beacon is at x=2, y=10
                       Sensor at x=0, y=11: closest beacon is at x=2, y=10
                       Sensor at x=20, y=14: closest beacon is at x=25, y=17
                       Sensor at x=17, y=20: closest beacon is at x=21, y=22
                       Sensor at x=16, y=7: closest beacon is at x=15, y=3
                       Sensor at x=14, y=3: closest beacon is at x=15, y=3
                       Sensor at x=20, y=1: closest beacon is at x=15, y=3
                       """;

        testSubject.rowToExamine = 10;
        
        assertEquals(26, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(4737567, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day15/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       Sensor at x=2, y=18: closest beacon is at x=-2, y=15
                       Sensor at x=9, y=16: closest beacon is at x=10, y=16
                       Sensor at x=13, y=2: closest beacon is at x=15, y=3
                       Sensor at x=12, y=14: closest beacon is at x=10, y=16
                       Sensor at x=10, y=20: closest beacon is at x=10, y=16
                       Sensor at x=14, y=17: closest beacon is at x=10, y=16
                       Sensor at x=8, y=7: closest beacon is at x=2, y=10
                       Sensor at x=2, y=0: closest beacon is at x=2, y=10
                       Sensor at x=0, y=11: closest beacon is at x=2, y=10
                       Sensor at x=20, y=14: closest beacon is at x=25, y=17
                       Sensor at x=17, y=20: closest beacon is at x=21, y=22
                       Sensor at x=16, y=7: closest beacon is at x=15, y=3
                       Sensor at x=14, y=3: closest beacon is at x=15, y=3
                       Sensor at x=20, y=1: closest beacon is at x=15, y=3
                       """;

        testSubject.rowToExamine = 10;

        assertEquals(56000011, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(13267474686239L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day15/input"))));
    }
}