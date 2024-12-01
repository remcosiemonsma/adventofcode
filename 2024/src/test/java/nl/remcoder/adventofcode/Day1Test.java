package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {
    private Day1 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day1();
    }

    @Test
    void part1Case1() {
        String data = """
                      3   4
                      4   3
                      2   5
                      1   3
                      3   9
                      3   3
                      """;

        assertEquals(11, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(3714264, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      3   4
                      4   3
                      2   5
                      1   3
                      3   9
                      3   3
                      """;

        assertEquals(31, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(18805872, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))));
    }
}