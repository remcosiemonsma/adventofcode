package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day9Test {
    private Day9 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day9();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       R 4
                       U 4
                       L 3
                       D 1
                       R 4
                       D 1
                       L 5
                       R 2
                       """;

        assertEquals(13, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(6498, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day9/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       R 4
                       U 4
                       L 3
                       D 1
                       R 4
                       D 1
                       L 5
                       R 2
                       """;

        assertEquals(1, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case2() {
        String input = """
                       R 5
                       U 8
                       L 8
                       D 3
                       R 17
                       D 10
                       L 25
                       U 20
                       """;

        assertEquals(36, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2531, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day9/input").toURI()))));
    }
}