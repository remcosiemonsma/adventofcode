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
                      Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                      Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                      Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                      Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                      Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                      Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
                      """;

        assertEquals(13, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(26346, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day4/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                      Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                      Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                      Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                      Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                      Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
                      """;

        assertEquals(30, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(8467762, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day4/input"))));
    }
}