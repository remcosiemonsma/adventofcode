package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test {
    private Day8 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day8();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       30373
                       25512
                       65332
                       33549
                       35390
                       """;

        assertEquals(21, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1840, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       30373
                       25512
                       65332
                       33549
                       35390
                       """;

        assertEquals(8, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(405769, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }
}