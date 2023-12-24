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
                      19, 13, 30 @ -2, 1, -2
                      18, 19, 22 @ -1, -1, -2
                      20, 25, 34 @ -2, -2, -4
                      12, 31, 28 @ -1, -2, -1
                      20, 19, 15 @ 1, -5, -3
                      """;

        testSubject.setMinIntersectionPoint(7);
        testSubject.setMaxIntersectionPoint(27);

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setMinIntersectionPoint(200000000000000L);
        testSubject.setMaxIntersectionPoint(400000000000000L);

        assertEquals(20361, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      19, 13, 30 @ -2, 1, -2
                      18, 19, 22 @ -1, -1, -2
                      20, 25, 34 @ -2, -2, -4
                      12, 31, 28 @ -1, -2, -1
                      20, 19, 15 @ 1, -5, -3
                      """;

        assertEquals(47, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(558415252330828L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI()))));
    }
}