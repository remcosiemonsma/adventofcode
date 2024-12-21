package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day21Test {
    private Day21 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day21();
    }

    @Test
    void part1Case1() {
        String data = """
                      029A
                      980A
                      179A
                      456A
                      379A
                      """;

        assertEquals(126384, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(179444, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day21/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      029A
                      980A
                      179A
                      456A
                      379A
                      """;

        assertEquals(154115708116294L, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(223285811665866L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day21/input"))));
    }
}