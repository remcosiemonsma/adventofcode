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
    void part1Case1() {
        String data = """
                      depth: 510
                      target: 10,10
                      """;

        assertEquals(114, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(10204, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      depth: 510
                      target: 10,10
                      """;

        assertEquals(45, testSubject.handlePart2(data.lines()));
    }
    
    @Test
    void testPart2Input() throws Exception {
        assertEquals(1004, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI()))));
    }
}