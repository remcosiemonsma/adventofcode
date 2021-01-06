package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day4Test {
    private Day4 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day4();
    }

    @Test
    public void part1Case1() {
        String data = "abcdef";

        assertEquals(609043, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case2() {
        String data = "pqrstuv";

        assertEquals(1048970, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(117946, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(3938038, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()))));
    }
}