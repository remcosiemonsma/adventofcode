package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Test {
    private Day3 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day3();
    }

    @Test
    public void part1Case1() {
        String data = ">";

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case2() {
        String data = "^>v<";

        assertEquals(4, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case3() {
        String data = "^v^v^v^v^v";

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(2565, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day3/input"))));
    }

    @Test
    public void part2Case1() {
        String data = "^v";

        assertEquals(3, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case2() {
        String data = "^>v<";

        assertEquals(3, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case3() {
        String data = "^v^v^v^v^v";

        assertEquals(11, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(2639, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day3/input"))));
    }
}