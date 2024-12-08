package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day22Test {
    private Day22 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day22();
    }

    @Test
    void part1Case1() {
        String data = """
                      ..#
                      #..
                      ...
                      """;

        assertEquals(5587, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(5223, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day22/input"))));
    }


    @Test
    void part2Case1() {
        String data = """
                      ..#
                      #..
                      ...
                      """;

        assertEquals(2511944, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2511456, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2017/day22/input"))));
    }
}