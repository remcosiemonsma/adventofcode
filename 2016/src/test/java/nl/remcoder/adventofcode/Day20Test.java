package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day20Test {
    private Day20 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day20();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(14975795, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day20/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(101, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day20/input"))));
    }
}