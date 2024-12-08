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
                      Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                      Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                      Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                      Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                      Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
                      """;

        assertEquals(8, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(2204, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day2/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                      Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                      Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                      Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                      Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
                      """;

        assertEquals(2286, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(71036, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day2/input"))));
    }
}