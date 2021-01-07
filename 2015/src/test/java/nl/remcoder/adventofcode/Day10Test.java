package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day10Test {
    private Day10 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day10();
    }

    @Test
    public void part1Case1() {
        String data = "1";

        assertEquals(82350, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(252594, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "1";

        assertEquals(1166642, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(3579328, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))));
    }
}