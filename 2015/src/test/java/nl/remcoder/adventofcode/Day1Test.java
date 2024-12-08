package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {
    private Day1 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day1();
    }

    @Test
    public void part1Case1() {
        String data = "(())";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case2() {
        String data = "()()";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case3() {
        String data = "(((";

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case4() {
        String data = "(()(()(";

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case5() {
        String data = "))(((((";

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case6() {
        String data = "())";

        assertEquals(-1, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case7() {
        String data = "))(";

        assertEquals(-1, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case8() {
        String data = ")))";

        assertEquals(-3, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case9() {
        String data = ")())())";

        assertEquals(-3, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(280, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day1/input"))));
    }

    @Test
    public void part2Case1() {
        String data = ")";

        assertEquals(1, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case2() {
        String data = "()())";

        assertEquals(5, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(1797, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day1/input"))));
    }
}