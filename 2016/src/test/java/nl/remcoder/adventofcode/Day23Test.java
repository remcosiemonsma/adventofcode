package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day23Test {
    private Day23 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day23();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(11123, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day23/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(479007683, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day23/input"))));
    }
}