package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {
    private Day1 testSubject;

    @BeforeEach
    public void beforeEach() {
        testSubject = new Day1();
    }

    @Test
    public void testPart1Case1() {
        var input = "12";

        assertEquals(2, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case2() {
        var input = "14";

        assertEquals(2, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case3() {
        var input = "1969";

        assertEquals(654, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case4() {
        var input = "100756";

        assertEquals(33583, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(3367126, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2019/day1/input"))));
    }

    @Test
    public void testPart2Case1() {
        var input = "14";

        assertEquals(2, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case2() {
        var input = "1969";

        assertEquals(966, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case3() {
        var input = "100756";

        assertEquals(50346, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(5047796, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2019/day1/input"))));
    }
}