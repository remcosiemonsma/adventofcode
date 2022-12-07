package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class Day14Test {
    private Day14 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day14();
    }

    @Test
    public void part1Case1() throws Exception {
        String data = "abc";

        assertEquals(22728, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(25427, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day14/input").toURI()))));
    }

    @Test
    public void part2Case1() throws Exception {
        String data = "abc";

        assertEquals(22551, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(22045, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day14/input").toURI()))));
    }
}