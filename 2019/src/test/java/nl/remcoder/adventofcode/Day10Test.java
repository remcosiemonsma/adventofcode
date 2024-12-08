package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {
    private Day10 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day10();
    }

    @Test
    void testPart1Case1() {
        var input = """
                    .#..#
                    .....
                    #####
                    ....#
                    ...##
                    """;

        assertEquals(8, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        var input = """
                    ......#.#.
                    #..#.#....
                    ..#######.
                    .#.#.###..
                    .#..#.....
                    ..#....#.#
                    #..#....#.
                    .##.#..###
                    ##...#..#.
                    .#....####
                    """;

        assertEquals(33, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case3() {
        var input = """
                    #.#...#.#.
                    .###....#.
                    .#....#...
                    ##.#.#.#.#
                    ....#.#.#.
                    .##..###.#
                    ..#...##..
                    ..##....##
                    ......#...
                    .####.###.
                    """;

        assertEquals(35, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case4() {
        var input = """
                    .#..#..###
                    ####.###.#
                    ....###.#.
                    ..###.##.#
                    ##.##.#.#.
                    ....###..#
                    ..#.#..#.#
                    #..#.#.###
                    .##...##.#
                    .....#.#..
                    """;

        assertEquals(41, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case5() {
        var input = """
                    .#..##.###...#######
                    ##.############..##.
                    .#.######.########.#
                    .###.#######.####.#.
                    #####.##.#.##.###.##
                    ..#####..#.#########
                    ####################
                    #.####....###.#.#.##
                    ##.#################
                    #####.##.###..####..
                    ..######..##.#######
                    ####.##.####...##..#
                    .#####..#.######.###
                    ##...#.##########...
                    #.##########.#######
                    .####.#.###.###.#.##
                    ....##.##.###..#####
                    .#.#.###########.###
                    #.#.#.#####.####.###
                    ###.##.####.##.#..##
                    """;

        assertEquals(210, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(282, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2019/day10/input"))));
    }

    @Test
    void testPart2Case1() {
        var input = """
                    .#..##.###...#######
                    ##.############..##.
                    .#.######.########.#
                    .###.#######.####.#.
                    #####.##.#.##.###.##
                    ..#####..#.#########
                    ####################
                    #.####....###.#.#.##
                    ##.#################
                    #####.##.###..####..
                    ..######..##.#######
                    ####.##.####...##..#
                    .#####..#.######.###
                    ##...#.##########...
                    #.##########.#######
                    .####.#.###.###.#.##
                    ....##.##.###..#####
                    .#.#.###########.###
                    #.#.#.#####.####.###
                    ###.##.####.##.#..##
                    """;

        assertEquals(802, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1008, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2019/day10/input"))));
    }
}
