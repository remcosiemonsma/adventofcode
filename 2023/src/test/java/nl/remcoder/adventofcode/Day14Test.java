package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    private Day14 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day14();
    }

    @Test
    void part1Case1() {
        String data = """
                      O....#....
                      O.OO#....#
                      .....##...
                      OO.#O....O
                      .O.....O#.
                      O.#..O.#.#
                      ..O..#O..O
                      .......O..
                      #....###..
                      #OO..#....
                      """;

        assertEquals(136, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(111979, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day14/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      O....#....
                      O.OO#....#
                      .....##...
                      OO.#O....O
                      .O.....O#.
                      O.#..O.#.#
                      ..O..#O..O
                      .......O..
                      #....###..
                      #OO..#....
                      """;

        assertEquals(64, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(102055, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day14/input"))));
    }
}