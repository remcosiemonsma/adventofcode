package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {
    private Day16 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day16();
    }

    @Test
    void part1Case1() {
        String data = """
                      ###############
                      #.......#....E#
                      #.#.###.#.###.#
                      #.....#.#...#.#
                      #.###.#####.#.#
                      #.#.#.......#.#
                      #.#.#####.###.#
                      #...........#.#
                      ###.#.#####.#.#
                      #...#.....#.#.#
                      #.#.#.###.#.#.#
                      #.....#...#.#.#
                      #.###.#.#.#.#.#
                      #S..#.....#...#
                      ###############
                      """;

        assertEquals(7036, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      #################
                      #...#...#...#..E#
                      #.#.#.#.#.#.#.#.#
                      #.#.#.#...#...#.#
                      #.#.#.#.###.#.#.#
                      #...#.#.#.....#.#
                      #.#.#.#.#.#####.#
                      #.#...#.#.#.....#
                      #.#.#####.#.###.#
                      #.#.#.......#...#
                      #.#.###.#####.###
                      #.#.#...#.....#.#
                      #.#.#.#####.###.#
                      #.#.#.........#.#
                      #.#.#.#########.#
                      #S#.............#
                      #################
                      """;

        assertEquals(11048, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(160624, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day16/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      ###############
                      #.......#....E#
                      #.#.###.#.###.#
                      #.....#.#...#.#
                      #.###.#####.#.#
                      #.#.#.......#.#
                      #.#.#####.###.#
                      #...........#.#
                      ###.#.#####.#.#
                      #...#.....#.#.#
                      #.#.#.###.#.#.#
                      #.....#...#.#.#
                      #.###.#.#.#.#.#
                      #S..#.....#...#
                      ###############
                      """;

        assertEquals(45, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      #################
                      #...#...#...#..E#
                      #.#.#.#.#.#.#.#.#
                      #.#.#.#...#...#.#
                      #.#.#.#.###.#.#.#
                      #...#.#.#.....#.#
                      #.#.#.#.#.#####.#
                      #.#...#.#.#.....#
                      #.#.#####.#.###.#
                      #.#.#.......#...#
                      #.#.###.#####.###
                      #.#.#...#.....#.#
                      #.#.#.#####.###.#
                      #.#.#.........#.#
                      #.#.#.#########.#
                      #S#.............#
                      #################
                      """;

        assertEquals(64, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(692, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day16/input"))));
    }
}