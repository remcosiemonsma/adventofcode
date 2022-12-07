package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class Day5Test {
    private Day5 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day5();
    }

    @Test
    public void part1Case1() throws Exception {
        String data = "abc";

        assertEquals("18f47a30", testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals("c6697b55", testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }

    @Test
    public void part2Case1() throws Exception {
        String data = "abc";

        assertEquals("05ace8e3", testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals("8c35d1ab", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }
}