package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day20Test {
    private Day20 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day20();
    }

    @Test
    void part1Case1() {
        String data = """
                      ###############
                      #...#...#.....#
                      #.#.#.#.#.###.#
                      #S#...#.#.#...#
                      #######.#.#.###
                      #######.#.#...#
                      #######.#.###.#
                      ###..E#...#...#
                      ###.#######.###
                      #...###...#...#
                      #.#####.#.###.#
                      #.#...#.#.#...#
                      #.#.#.#.#.#.###
                      #...#...#...###
                      ###############
                      """;

        testSubject.setWantedCheatDistance(1);

        assertEquals(44, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setWantedCheatDistance(100);

        assertEquals(1332, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day20/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      ###############
                      #...#...#.....#
                      #.#.#.#.#.###.#
                      #S#...#.#.#...#
                      #######.#.#.###
                      #######.#.#...#
                      #######.#.###.#
                      ###..E#...#...#
                      ###.#######.###
                      #...###...#...#
                      #.#####.#.###.#
                      #.#...#.#.#...#
                      #.#.#.#.#.#.###
                      #...#...#...###
                      ###############
                      """;

        testSubject.setWantedCheatDistance(50);

        assertEquals(285, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setWantedCheatDistance(100);

        assertEquals(987695, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day20/input"))));
    }
}