package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    private Day12 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day12();
    }

    @Test
    void part1Case1() {
        String data = """
                      initial state: #..#.#..##......###...###
                                            
                      ...## => #
                      ..#.. => #
                      .#... => #
                      .#.#. => #
                      .#.## => #
                      .##.. => #
                      .#### => #
                      #.#.# => #
                      #.### => #
                      ##.#. => #
                      ##.## => #
                      ###.. => #
                      ###.# => #
                      ####. => #
                      """;

        assertEquals(325, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(2571, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day12/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(3100000000655L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day12/input"))));
    }
}