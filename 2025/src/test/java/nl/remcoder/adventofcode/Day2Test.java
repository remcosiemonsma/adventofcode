package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {
    private Day2 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day2();
    }

    @Test
    void part1Case1() {
        String data = """
                      11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
                      """;

        assertEquals(1227775554, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(13108371860L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2025/day2/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
                      """;

        assertEquals(4174379265L, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(22471660255L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2025/day2/input"))));
    }
}