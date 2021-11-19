package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Day2Test {
    private Day2 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day2();
    }

    @Test
    public void part1Case1() {
        String data = "ULL\n" +
                      "RRDDD\n" +
                      "LURDL\n" +
                      "UUUUD";

        assertEquals("1985", testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals("45973", testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "ULL\n" +
                      "RRDDD\n" +
                      "LURDL\n" +
                      "UUUUD";

        assertEquals("5DB3", testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals("27CA4", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()))));
    }
}