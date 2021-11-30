package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day12Test {
    private Day12 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day12();
    }

    @Test
    public void part1Case1() {
        String data = "cpy 41 a\n" +
                      "inc a\n" +
                      "inc a\n" +
                      "dec a\n" +
                      "jnz a 2\n" +
                      "dec a";

        assertEquals(42, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(318117, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(9227771, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))));
    }
}