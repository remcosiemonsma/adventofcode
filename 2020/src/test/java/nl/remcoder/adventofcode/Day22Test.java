package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day22Test {
    private Day22 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day22();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       Player 1:
                       9
                       2
                       6
                       3
                       1
                                       
                       Player 2:
                       5
                       8
                       4
                       7
                       10
                       """;

        assertEquals(306, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(32472, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2020/day22/input"))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       Player 1:
                       9
                       2
                       6
                       3
                       1
                                       
                       Player 2:
                       5
                       8
                       4
                       7
                       10
                       """;

        assertEquals(291, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(36463, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2020/day22/input"))));
    }
}