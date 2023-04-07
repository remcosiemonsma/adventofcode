package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {
    private Day17 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day17();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       target area: x=20..30, y=-10..-5
                       """;

        assertEquals(45, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(8646, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       target area: x=20..30, y=-10..-5
                       """;

        assertEquals(112, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(5945, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()))));
    }
}