package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day23Test {
    private Day23 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day23();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(8281, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(911, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }
}