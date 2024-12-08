package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {
    private Day13 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day13();
    }

    @Test
    void part1Case1() {
        String data = """
                      /->-\\       \s
                      |   |  /----\\
                      | /-+--+-\\  |
                      | | |  | v  |
                      \\-+-/  \\-+--/
                        \\------/  \s
                      """;

        assertArrayEquals(new int[] {7, 3}, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertArrayEquals(new int[] {8, 9}, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day13/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      />-<\\ \s
                      |   | \s
                      | /<+-\\
                      | | | v
                      \\>+</ |
                        |   ^
                        \\<->/
                      """;

        assertArrayEquals(new int[] {6, 4}, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertArrayEquals(new int[] {73, 33}, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day13/input"))));
    }
}