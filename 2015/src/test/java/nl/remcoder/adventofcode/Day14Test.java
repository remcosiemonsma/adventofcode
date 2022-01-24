package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day14Test {
    private Day14 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day14();
    }

    @Test
    public void part1Case1() {
        String data = "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.\n" +
                      "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.";

        assertEquals(2660, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(2640, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day14/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.\n" +
                      "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.";

        assertEquals(1564, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(1102, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day14/input").toURI()))));
    }
}