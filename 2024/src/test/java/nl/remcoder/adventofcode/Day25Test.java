package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {
    private Day25 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day25();
    }

    @Test
    void part1Case1() {
        String data = """
                      #####
                      .####
                      .####
                      .####
                      .#.#.
                      .#...
                      .....
                      
                      #####
                      ##.##
                      .#.##
                      ...##
                      ...#.
                      ...#.
                      .....
                      
                      .....
                      #....
                      #....
                      #...#
                      #.#.#
                      #.###
                      #####
                      
                      .....
                      .....
                      #.#..
                      ###..
                      ###.#
                      ###.#
                      #####
                      
                      .....
                      .....
                      .....
                      #....
                      #.#..
                      #.#.#
                      #####
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(3116, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day25/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("Merry Christmas!", testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day25/input"))));
    }
}