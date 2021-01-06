package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Day3Test {
    private Day3 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day3();
    }

    @Test
    public void part1Case1() {
        String data = ">";

        assertEquals(2, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case2() {
        String data = "^>v<";

        assertEquals(4, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case3() {
        String data = "^v^v^v^v^v";

        assertEquals(2, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(2565, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "^v";

        assertEquals(3, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part2Case2() {
        String data = "^>v<";

        assertEquals(3, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part2Case3() {
        String data = "^v^v^v^v^v";

        assertEquals(11, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(2639, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))));
    }
}