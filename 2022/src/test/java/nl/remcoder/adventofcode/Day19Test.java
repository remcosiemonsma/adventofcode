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
    void testPart1Case1() {
        String input = """
                       Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
                       Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
                       """;

        assertEquals(33, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1262, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day19/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
                       Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
                       """;

        assertEquals(3472, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(37191, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day19/input"))));
    }
}