package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day8Test {
    private Day8 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day8();
    }

    @Test
    public void part1Case1() {
        String data = "rect 3x2\n" +
                      "rotate column x=1 by 1\n" +
                      "rotate row y=0 by 4\n" +
                      "rotate column x=1 by 1";

        assertEquals(6, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(119, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals("ZFHFSFOGPO", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }
}