package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day21Test {
    private Day21 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day21();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(167, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day21/input"))));
    }
    
    @Test
    void testPart2Input() throws Exception {
        assertEquals(2425195, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2017/day21/input"))));
    }
}