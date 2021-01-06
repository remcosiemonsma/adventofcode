package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day9Test {
    private Day9 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day9();
    }

    @Test
    public void part1Case1() {
        String data = "London to Dublin = 464\n" +
                      "London to Belfast = 518\n" +
                      "Dublin to Belfast = 141";

        assertEquals(605, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(141, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day9/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "London to Dublin = 464\n" +
                      "London to Belfast = 518\n" +
                      "Dublin to Belfast = 141";

        assertEquals(982, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(736, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day9/input").toURI()))));
    }
}