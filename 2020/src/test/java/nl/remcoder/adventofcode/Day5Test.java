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
    public void testPart1Case1() {
        String input = """
                       BFFFBBFRRR
                       FFFBBBFRRR
                       BBFFBBFRLL
                       """;

        assertEquals(820, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(816, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2020/day5/input"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(539, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2020/day5/input"))));
    }
}