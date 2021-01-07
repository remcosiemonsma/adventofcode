package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day11Test {
    private Day11 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day11();
    }

    @Test
    public void part1Case1() {
        String data = "abcdefgh";

        assertEquals("abcdffaa", testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case2() {
        String data = "ghijklmn";

        assertEquals("ghjaabcc", testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals("hepxxyzz", testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "abcdefgh";

        assertEquals("abcdffbb", testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part2Case2() {
        String data = "ghijklmn";

        assertEquals("ghjbbcdd", testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals("heqaabcc", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI()))));
    }
}