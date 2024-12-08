package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test {
    private Day18 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day18();
    }

    @Test
    void part1Case1() {
        String data = """
                      .#.#...|#.
                      .....#|##|
                      .|..|...#.
                      ..|#.....#
                      #.#|||#|#|
                      ...#.||...
                      .|....|...
                      ||...#|.#|
                      |.||||..|.
                      ...#.|..|.
                      """;

        assertEquals(1147, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(589931, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day18/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(222332, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day18/input"))));
    }
}