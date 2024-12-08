package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Test {
    private Day18 testSubject;

    @BeforeEach
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
                Files.lines(Paths.get("../aoc-inputs/2015/day18/input"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(781, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day18/input"))));
    }
}