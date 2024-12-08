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
    void testPart1Case1() {
        String input = """
                           [D]   \s
                       [N] [C]   \s
                       [Z] [M] [P]
                        1   2   3\s
                                             
                       move 1 from 2 to 1
                       move 3 from 1 to 3
                       move 2 from 2 to 1
                       move 1 from 1 to 2
                       """;

        assertEquals("CMZ", testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals("TPGVQPFDH", testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day5/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                           [D]   \s
                       [N] [C]   \s
                       [Z] [M] [P]
                        1   2   3\s
                                             
                       move 1 from 2 to 1
                       move 3 from 1 to 3
                       move 2 from 2 to 1
                       move 1 from 1 to 2
                       """;

        assertEquals("MCD", testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("DMRDFRHHH", testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day5/input"))));
    }
}