package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {
    private Day17 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day17();
    }

    @Test
    void part1Case1() {
        String data = """
                      2413432311323
                      3215453535623
                      3255245654254
                      3446585845452
                      4546657867536
                      1438598798454
                      4457876987766
                      3637877979653
                      4654967986887
                      4564679986453
                      1224686865563
                      2546548887735
                      4322674655533
                      """;

        assertEquals(102, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1004, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day17/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      2413432311323
                      3215453535623
                      3255245654254
                      3446585845452
                      4546657867536
                      1438598798454
                      4457876987766
                      3637877979653
                      4654967986887
                      4564679986453
                      1224686865563
                      2546548887735
                      4322674655533
                      """;

        assertEquals(94, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      111111111111
                      999999999991
                      999999999991
                      999999999991
                      999999999991
                      """;

        assertEquals(71, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1171, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day17/input"))));
    }
}