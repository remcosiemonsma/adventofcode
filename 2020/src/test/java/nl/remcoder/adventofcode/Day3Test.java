package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {
    private Day3 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day3();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       ..##.......
                       #...#...#..
                       .#....#..#.
                       ..#.#...#.#
                       .#...##..#.
                       ..#.##.....
                       .#.#.#....#
                       .#........#
                       #.##...#...
                       #...##....#
                       .#..#...#.#
                       """;

        assertEquals(7, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(259, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       ..##.......
                       #...#...#..
                       .#....#..#.
                       ..#.#...#.#
                       .#...##..#.
                       ..#.##.....
                       .#.#.#....#
                       .#........#
                       #.##...#...
                       #...##....#
                       .#..#...#.#
                       """;

        assertEquals(336, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(2224913600L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))));
    }
}