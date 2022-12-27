package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    private Day24 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day24();
    }

    @Test
    void part1Case1() {
        String data = """
                      0/2
                      2/2
                      2/3
                      3/4
                      3/5
                      0/1
                      10/1
                      9/10
                      """;

        assertEquals(31, testSubject.handlePart1(data.lines()));
    }
    
    @Test
    void testPart1Input() throws Exception {
        assertEquals(1511, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI()))));
    }
    
    @Test
    void part2Case1() {
        String data = """
                      0/2
                      2/2
                      2/3
                      3/4
                      3/5
                      0/1
                      10/1
                      9/10
                      """;

        assertEquals(19, testSubject.handlePart2(data.lines()));
    }
    
    @Test
    void testPart2Input() throws Exception {
        assertEquals(1471, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI()))));
    }
}