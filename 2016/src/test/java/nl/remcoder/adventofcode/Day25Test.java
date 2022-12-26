package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {
    private Day25 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day25();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(180, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day25/input").toURI()))));
    }
}