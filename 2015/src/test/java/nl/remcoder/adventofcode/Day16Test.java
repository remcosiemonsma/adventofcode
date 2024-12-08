package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Test {
    private Day16 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day16();
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(103, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day16/input"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(405, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day16/input"))));
    }
}