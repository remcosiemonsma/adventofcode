package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day1Test {
    private Day1 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day1();
    }

    @Test
    public void part1Case1() {
        String data = "R2, L3";

        assertEquals(5, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case2() {
        String data = "R2, R2, R2";

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case3() {
        String data = "R5, L5, R5, R3";

        assertEquals(12, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(239, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "R8, R4, R4, R8";

        assertEquals(4, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(141, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))));
    }
}