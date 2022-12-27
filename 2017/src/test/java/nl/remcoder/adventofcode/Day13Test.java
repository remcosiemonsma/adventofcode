package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {
    private Day13 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day13();
    }

    @Test
    void part1Case1() {
        String data = """
                      0: 3
                      1: 2
                      4: 4
                      6: 4
                      """;

        assertEquals(24, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1960, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day13/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      0: 3
                      1: 2
                      4: 4
                      6: 4
                      """;

        assertEquals(10, testSubject.handlePart2(data.lines()));
    }
    @Test
    void testPart2Input() throws Exception {
        assertEquals(3903378, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day13/input").toURI()))));
    }
}