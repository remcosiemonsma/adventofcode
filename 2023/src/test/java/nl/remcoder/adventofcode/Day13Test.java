package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {
    private Day13 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day13();
    }

    @Test
    void part1Case1() {
        String data = """
                      #.##..##.
                      ..#.##.#.
                      ##......#
                      ##......#
                      ..#.##.#.
                      ..##..##.
                      #.#.##.#.
                      
                      #...##..#
                      #....#..#
                      ..##..###
                      #####.##.
                      #####.##.
                      ..##..###
                      #....#..#
                      """;

        assertEquals(405, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(37113, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day13/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      #.##..##.
                      ..#.##.#.
                      ##......#
                      ##......#
                      ..#.##.#.
                      ..##..##.
                      #.#.##.#.
                      
                      #...##..#
                      #....#..#
                      ..##..###
                      #####.##.
                      #####.##.
                      ..##..###
                      #....#..#
                      """;

        assertEquals(400, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(30449, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day13/input"))));
    }
}