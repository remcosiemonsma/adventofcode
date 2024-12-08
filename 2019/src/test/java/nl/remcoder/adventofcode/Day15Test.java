package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {
    private Day15 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day15();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(220, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2019/day15/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(334, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2019/day15/input"))));
    }
}