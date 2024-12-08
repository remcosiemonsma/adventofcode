package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {
    private Day3 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day3();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(419, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day3/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(295229, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2017/day3/input"))));
    }
}