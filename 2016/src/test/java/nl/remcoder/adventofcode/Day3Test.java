package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day3Test {
    private Day3 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day3();
    }

    @Test
    public void part1Case1() {
        String data = "5 10 25";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(869, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "101 301 501\n" +
                      "102 302 502\n" +
                      "103 303 503\n" +
                      "201 401 601\n" +
                      "202 402 602\n" +
                      "203 403 603";

        assertEquals(6, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(1544, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))));
    }
}