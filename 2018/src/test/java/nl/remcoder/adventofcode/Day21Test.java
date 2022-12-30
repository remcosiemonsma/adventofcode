package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day21Test {
    private Day21 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day21();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(16457176, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day21/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(13625951, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day21/input").toURI()))));
    }
}