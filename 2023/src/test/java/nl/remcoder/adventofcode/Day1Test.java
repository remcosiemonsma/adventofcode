package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {
    private Day1 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day1();
    }

    @Test
    void part1Case1() {
        String data = """
                      1abc2
                      pqr3stu8vwx
                      a1b2c3d4e5f
                      treb7uchet
                      """;

        assertEquals(142, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(55017, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day1/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      two1nine
                      eightwothree
                      abcone2threexyz
                      xtwone3four
                      4nineeightseven2
                      zoneight234
                      7pqrstsixteen
                      """;

        assertEquals(281, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(53539, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day1/input"))));
    }
}