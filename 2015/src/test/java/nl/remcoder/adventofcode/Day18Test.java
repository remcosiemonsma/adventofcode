package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Test {
    private Day18 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day18();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       .#.#.#
                       ...##.
                       #....#
                       ..#...
                       #.#..#
                       ####..
                       """;

        assertEquals(4, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(768, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(781, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()))));
    }
}