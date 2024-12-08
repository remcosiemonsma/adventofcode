package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {
    private Day10 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day10();
    }

    @Test
    public void part1Case1() {
        String data = "1";

        assertEquals(82350, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(252594, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day10/input"))));
    }

    @Test
    public void part2Case1() {
        String data = "1";

        assertEquals(1166642, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(3579328, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day10/input"))));
    }
}