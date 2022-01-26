package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class Day17Test {
    private Day17 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day17();
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(4372, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(4, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()))));
    }
}