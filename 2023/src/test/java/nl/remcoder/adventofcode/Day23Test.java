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
    void part1Case1() {
        String data = """
                      #.#####################
                      #.......#########...###
                      #######.#########.#.###
                      ###.....#.>.>.###.#.###
                      ###v#####.#v#.###.#.###
                      ###.>...#.#.#.....#...#
                      ###v###.#.#.#########.#
                      ###...#.#.#.......#...#
                      #####.#.#.#######.#.###
                      #.....#.#.#.......#...#
                      #.#####.#.#.#########v#
                      #.#...#...#...###...>.#
                      #.#.#v#######v###.###v#
                      #...#.>.#...>.>.#.###.#
                      #####v#.#.###v#.#.###.#
                      #.....#...#...#.#.#...#
                      #.#########.###.#.#.###
                      #...###...#...#...#.###
                      ###.###.#.###v#####v###
                      #...#...#.#.>.>.#.>.###
                      #.###.###.#.###.#.#v###
                      #.....###...###...#...#
                      #####################.#
                      """;

        assertEquals(94, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(2366, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day23/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      #.#####################
                      #.......#########...###
                      #######.#########.#.###
                      ###.....#.>.>.###.#.###
                      ###v#####.#v#.###.#.###
                      ###.>...#.#.#.....#...#
                      ###v###.#.#.#########.#
                      ###...#.#.#.......#...#
                      #####.#.#.#######.#.###
                      #.....#.#.#.......#...#
                      #.#####.#.#.#########v#
                      #.#...#...#...###...>.#
                      #.#.#v#######v###.###v#
                      #...#.>.#...>.>.#.###.#
                      #####v#.#.###v#.#.###.#
                      #.....#...#...#.#.#...#
                      #.#########.###.#.#.###
                      #...###...#...#...#.###
                      ###.###.#.###v#####v###
                      #...#...#.#.>.>.#.>.###
                      #.###.###.#.###.#.#v###
                      #.....###...###...#...#
                      #####################.#
                      """;

        assertEquals(154, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(6682, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day23/input"))));
    }
}