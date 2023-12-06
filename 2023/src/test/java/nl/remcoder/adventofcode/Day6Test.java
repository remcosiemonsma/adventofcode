package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {
    private Day6 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day6();
    }

    @Test
    void part1Case1() {
        String data = """
                      Time:      7  15   30
                      Distance:  9  40  200
                      """;

        assertEquals(288, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(2756160, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      Time:      7  15   30
                      Distance:  9  40  200
                      """;

        assertEquals(71503, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(34788142, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()))));
    }
}