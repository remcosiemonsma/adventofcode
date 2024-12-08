package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {
    private Day25 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day25();
    }

    @Test
    @Disabled("No dynamic solution build yet, solution hardcoded to actual input")
    void part1Case1() {
        String data = """
                      Begin in state A.
                      Perform a diagnostic checksum after 6 steps.
                                            
                      In state A:
                        If the current value is 0:
                          - Write the value 1.
                          - Move one slot to the right.
                          - Continue with state B.
                        If the current value is 1:
                          - Write the value 0.
                          - Move one slot to the left.
                          - Continue with state B.
                                            
                      In state B:
                        If the current value is 0:
                          - Write the value 1.
                          - Move one slot to the left.
                          - Continue with state A.
                        If the current value is 1:
                          - Write the value 1.
                          - Move one slot to the right.
                          - Continue with state A.
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(2526, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day25/input"))));
    }
}