package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test {
    private Day17 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day17();
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(4372, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day17/input"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(4, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day17/input"))));
    }
}