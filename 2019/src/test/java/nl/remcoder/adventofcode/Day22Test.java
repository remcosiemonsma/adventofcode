package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day22Test {
    private Day22 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day22();
    }
    
    @Test
    void testPart1Input() throws Exception {
        assertEquals(3143, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(3920265924568L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI()))));
    }
}