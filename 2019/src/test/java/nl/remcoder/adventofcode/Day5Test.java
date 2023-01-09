package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
    private Day5 testSubject;

    @BeforeEach
    public void beforeEach() {
        testSubject = new Day5();
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(9025675, testSubject.handlePart1(Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(11981754, testSubject.handlePart2(Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }
}