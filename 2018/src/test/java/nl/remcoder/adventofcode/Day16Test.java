package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {
    private Day16 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day16();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(517, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(667, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()))));
    }
}