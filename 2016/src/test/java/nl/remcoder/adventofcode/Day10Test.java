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
                      value 5 goes to bot 2
                      bot 2 gives low to bot 1 and high to bot 0
                      value 3 goes to bot 1
                      bot 1 gives low to output 1 and high to bot 0
                      bot 0 gives low to output 2 and high to output 0
                      value 2 goes to bot 2
                      """;

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(147, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day10/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      value 5 goes to bot 2
                      bot 2 gives low to bot 1 and high to bot 0
                      value 3 goes to bot 1
                      bot 1 gives low to output 1 and high to bot 0
                      bot 0 gives low to output 2 and high to output 0
                      value 2 goes to bot 2
                      """;

        assertEquals(30, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(55637, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day10/input"))));
    }
}