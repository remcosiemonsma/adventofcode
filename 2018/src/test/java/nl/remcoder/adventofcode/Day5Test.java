package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
    private Day5 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day5();
    }

    @Test
    void part1Case1() {
        String data = """
                      aA
                      """;

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      abBA
                      """;

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = """
                      abAB
                      """;

        assertEquals(4, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = """
                      aabAAB
                      """;

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case5() {
        String data = """
                      dabAcCaCBAcCcaDA
                      """;

        assertEquals(10, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(10638, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day5/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      dabAcCaCBAcCcaDA
                      """;

        assertEquals(4, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(4944, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day5/input"))));
    }
}