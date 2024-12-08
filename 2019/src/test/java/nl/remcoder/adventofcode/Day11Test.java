package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {
    private Day11 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day11();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1747, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2019/day11/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("ZCGRHKLB", testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2019/day11/input"))));
    }
}