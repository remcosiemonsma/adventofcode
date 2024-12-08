package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    private Day24 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day24();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(52926995971999L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2021/day24/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(11811951311485L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2021/day24/input"))));
    }
}