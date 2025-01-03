package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {
    private Day15 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day15();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       1163751742
                       1381373672
                       2136511328
                       3694931569
                       7463417111
                       1319128137
                       1359912421
                       3125421639
                       1293138521
                       2311944581
                       """;

        assertEquals(40, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(458, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2021/day15/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       1163751742
                       1381373672
                       2136511328
                       3694931569
                       7463417111
                       1319128137
                       1359912421
                       3125421639
                       1293138521
                       2311944581
                       """;

        assertEquals(315, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2800, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2021/day15/input"))));
    }
}