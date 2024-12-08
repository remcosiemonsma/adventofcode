package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {
    private Day11 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day11();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       5483143223
                       2745854711
                       5264556173
                       6141336146
                       6357385478
                       4167524645
                       2176841721
                       6882881134
                       4846848554
                       5283751526
                       """;

        assertEquals(1656, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1659, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2021/day11/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       5483143223
                       2745854711
                       5264556173
                       6141336146
                       6357385478
                       4167524645
                       2176841721
                       6882881134
                       4846848554
                       5283751526
                       """;

        assertEquals(195, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(227, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2021/day11/input"))));
    }
}