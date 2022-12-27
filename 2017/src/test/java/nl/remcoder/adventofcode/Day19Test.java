package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day19Test {
    private Day19 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day19();
    }

    @Test
    void part1Case1() {
        String data = """
                          |         \s
                          |  +--+   \s
                          A  |  C   \s
                      F---|----E|--+\s
                          |  |  |  D\s
                          +B-+  +--+\s
                      """;

        assertEquals("ABCDEF", testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals("HATBMQJYZ", testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day19/input").toURI()))));
    }


    @Test
    void part2Case1() {
        String data = """
                          |         \s
                          |  +--+   \s
                          A  |  C   \s
                      F---|----E|--+\s
                          |  |  |  D\s
                          +B-+  +--+\s
                      """;

        assertEquals(38, testSubject.handlePart2(data.lines()));
    }
    
    @Test
    void testPart2Input() throws Exception {
        assertEquals(16332, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day19/input").toURI()))));
    }
}