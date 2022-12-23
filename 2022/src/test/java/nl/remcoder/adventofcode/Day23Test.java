package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day23Test {
    private Day23 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day23();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       ##
                       #.
                       ..
                       ##
                       """;

        assertEquals(25, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        String input = """
                       ....#..
                       ..###.#
                       #...#.#
                       .#...##
                       #.###..
                       ##.#.##
                       .#..#..
                       """;

        assertEquals(110, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(3874, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       ....#..
                       ..###.#
                       #...#.#
                       .#...##
                       #.###..
                       ##.#.##
                       .#..#..
                       """;

        assertEquals(20, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(948, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }
}