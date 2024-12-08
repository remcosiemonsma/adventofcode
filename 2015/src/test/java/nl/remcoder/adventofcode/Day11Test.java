package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {
    private Day11 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day11();
    }

    @Test
    public void part1Case1() {
        String data = "abcdefgh";

        assertEquals("abcdffaa", testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case2() {
        String data = "ghijklmn";

        assertEquals("ghjaabcc", testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals("hepxxyzz", testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day11/input"))));
    }

    @Test
    public void part2Case1() {
        String data = "abcdefgh";

        assertEquals("abcdffbb", testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case2() {
        String data = "ghijklmn";

        assertEquals("ghjbbcdd", testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals("heqaabcc", testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day11/input"))));
    }
}