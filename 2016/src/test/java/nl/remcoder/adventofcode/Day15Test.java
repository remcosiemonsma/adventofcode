package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {
    private Day15 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day15();
    }

    @Test
    void part1Case1() throws Exception {
        String data = """
                      Disc #1 has 5 positions; at time=0, it is at position 4.
                      Disc #2 has 2 positions; at time=0, it is at position 1.
                      """;

        assertEquals(5, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(121834, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(3208099, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }
}