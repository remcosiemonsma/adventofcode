package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {
    private Day7 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day7();
    }

    @Test
    void part1Case1() {
        String data = """
                      190: 10 19
                      3267: 81 40 27
                      83: 17 5
                      156: 15 6
                      7290: 6 8 6 15
                      161011: 16 10 13
                      192: 17 8 14
                      21037: 9 7 18 13
                      292: 11 6 16 20
                      """;

        assertEquals(3749, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(5030892084481L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day7/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      190: 10 19
                      3267: 81 40 27
                      83: 17 5
                      156: 15 6
                      7290: 6 8 6 15
                      161011: 16 10 13
                      192: 17 8 14
                      21037: 9 7 18 13
                      292: 11 6 16 20
                      """;

        assertEquals(11387, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(91377448644679L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day7/input"))));
    }
}