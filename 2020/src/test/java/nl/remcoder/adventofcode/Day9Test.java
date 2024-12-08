package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day9Test {
    private Day9 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day9();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       35
                       20
                       15
                       25
                       47
                       40
                       62
                       55
                       65
                       95
                       102
                       117
                       150
                       182
                       127
                       219
                       299
                       277
                       309
                       576
                       """;

        testSubject.setPreambleLength(5);

        assertEquals(127, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        testSubject.setPreambleLength(25);

        assertEquals(36845998, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2020/day9/input"))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       35
                       20
                       15
                       25
                       47
                       40
                       62
                       55
                       65
                       95
                       102
                       117
                       150
                       182
                       127
                       219
                       299
                       277
                       309
                       576
                       """;

        testSubject.setPreambleLength(5);

        assertEquals(62, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        testSubject.setPreambleLength(25);

        assertEquals(4830226, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2020/day9/input"))));
    }
}