package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class Day16Test {
    private Day16 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day16();
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(103, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(405, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()))));
    }
}