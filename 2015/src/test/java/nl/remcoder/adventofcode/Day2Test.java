package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Test {
    private Day2 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day2();
    }

    @Test
    public void part1Case1() {
        String data = "2x3x4";

        assertEquals(58, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case2() {
        String data = "1x1x10";

        assertEquals(43, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(1586300, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day2/input"))));
    }

    @Test
    public void part2Case1() {
        String data = "2x3x4";

        assertEquals(34, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case2() {
        String data = "1x1x10";

        assertEquals(14, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(3737498, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day2/input"))));
    }
}