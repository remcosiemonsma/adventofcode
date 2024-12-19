package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day19Test {
    private Day19 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day19();
    }

    @Test
    void part1Case1() {
        String data = """
                      r, wr, b, g, bwu, rb, gb, br
                      
                      brwrr
                      bggr
                      gbbr
                      rrbgbr
                      ubwu
                      bwurrg
                      brgr
                      bbrgwb
                      """;

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(233, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day19/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      r, wr, b, g, bwu, rb, gb, br
                      
                      brwrr
                      bggr
                      gbbr
                      rrbgbr
                      ubwu
                      bwurrg
                      brgr
                      bbrgwb
                      """;

        assertEquals(16, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(691316989225259L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day19/input"))));
    }
}