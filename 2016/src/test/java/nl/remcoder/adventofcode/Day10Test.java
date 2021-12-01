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
        String data = "value 5 goes to bot 2\n" +
                      "bot 2 gives low to bot 1 and high to bot 0\n" +
                      "value 3 goes to bot 1\n" +
                      "bot 1 gives low to output 1 and high to bot 0\n" +
                      "bot 0 gives low to output 2 and high to output 0\n" +
                      "value 2 goes to bot 2";

        assertEquals(0, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(147, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "value 5 goes to bot 2\n" +
                      "bot 2 gives low to bot 1 and high to bot 0\n" +
                      "value 3 goes to bot 1\n" +
                      "bot 1 gives low to output 1 and high to bot 0\n" +
                      "bot 0 gives low to output 2 and high to output 0\n" +
                      "value 2 goes to bot 2";

        assertEquals(30, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(55637, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))));
    }
}