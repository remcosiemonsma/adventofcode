package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {
    private Day7 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day7();
    }

    @Test
    void testPart1Case1() {
        String data = """
                      16,1,2,0,4,2,7,1,2,14
                      """;

        assertEquals(37, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(335330, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String data = """
                      16,1,2,0,4,2,7,1,2,14
                      """;

        assertEquals(168, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(92439766, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }
}