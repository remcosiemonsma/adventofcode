package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {
    private Day4 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day4();
    }

    @Test
    void part1Case1() {
        String data = """
                      MMMSXXMASM
                      MSAMXMSMSA
                      AMXSXMAAMM
                      MSAMASMSMX
                      XMASAMXAMM
                      XXAMMXXAMA
                      SMSMSASXSS
                      SAXAMASAAA
                      MAMMMXMMMM
                      MXMXAXMASX
                      """;

        assertEquals(18, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(2378, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day4/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      MMMSXXMASM
                      MSAMXMSMSA
                      AMXSXMAAMM
                      MSAMASMSMX
                      XMASAMXAMM
                      XXAMMXXAMA
                      SMSMSASXSS
                      SAXAMASAAA
                      MAMMMXMMMM
                      MXMXAXMASX
                      """;

        assertEquals(9, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1796, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day4/input"))));
    }
}