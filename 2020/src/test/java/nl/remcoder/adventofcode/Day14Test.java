package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    private Day14 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day14();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
                       mem[8] = 11
                       mem[7] = 101
                       mem[8] = 0
                       """;

        assertEquals(165, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(15172047086292L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2020/day14/input"))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       mask = 000000000000000000000000000000X1001X
                       mem[42] = 100
                       mask = 00000000000000000000000000000000X0XX
                       mem[26] = 1
                       """;

        assertEquals(208, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(4197941339968L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2020/day14/input"))));
    }
}