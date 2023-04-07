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
    public void testPart1Case1() {
        String input = """
                       .#.
                       ..#
                       ###
                       """;

        assertEquals(112, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(391, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       .#.
                       ..#
                       ###
                       """;

        assertEquals(848, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(2264, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()))));
    }
}