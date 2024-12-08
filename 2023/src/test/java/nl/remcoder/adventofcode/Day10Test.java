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
    void part1Case1() {
        String data = """
                      7-F7-
                      .FJ|7
                      SJLL7
                      |F--J
                      LJ.LJ
                      """;

        assertEquals(8, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(6870, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day10/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      ...........
                      .S-------7.
                      .|F-----7|.
                      .||.....||.
                      .||.....||.
                      .|L-7.F-J|.
                      .|..|.|..|.
                      .L--J.L--J.
                      ...........
                      """;

        assertEquals(4, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      .F----7F7F7F7F-7....
                      .|F--7||||||||FJ....
                      .||.FJ||||||||L7....
                      FJL7L7LJLJ||LJ.L-7..
                      L--J.L7...LJS7F-7L7.
                      ....F-J..F7FJ|L7L7L7
                      ....L7.F7||L7|.L7L7|
                      .....|FJLJ|FJ|F7|.LJ
                      ....FJL-7.||.||||...
                      ....L---J.LJ.LJLJ...
                      """;

        assertEquals(8, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case3() {
        String data = """
                      FF7FSF7F7F7F7F7F---7
                      L|LJ||||||||||||F--J
                      FL-7LJLJ||||||LJL-77
                      F--JF--7||LJLJ7F7FJ-
                      L---JF-JLJ.||-FJLJJ7
                      |F|F-JF---7F7-L7L|7|
                      |FFJF7L7F-JF7|JL---7
                      7-L-JL7||F7|L7F-7F7|
                      L.L7LFJ|||||FJL7||LJ
                      L7JLJL-JLJLJL--JLJ.L
                      """;

        assertEquals(10, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(287, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day10/input"))));
    }
}