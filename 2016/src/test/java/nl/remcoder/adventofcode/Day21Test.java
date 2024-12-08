package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day21Test {
    private Day21 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day21();
    }

    @Test
    void part1Case1() {
        String data = """
                      swap position 4 with position 0
                      swap letter d with letter b
                      reverse positions 0 through 4
                      rotate left 1 stepCrucible
                      move position 1 to position 4
                      move position 3 to position 0
                      rotate based on position of letter b
                      rotate based on position of letter d
                      """;

        testSubject.setPassword("abcde");
        
        assertEquals("decab", testSubject.handlePart1(data.lines()));
    }
    
    @Test
    void testPart1Input() throws Exception {
        testSubject.setPassword("abcdefgh");
        
        assertEquals("bfheacgd", testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day21/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setPassword("fbgdceah");
        
        assertEquals("gcehdbfa", testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day21/input"))));
    }
}