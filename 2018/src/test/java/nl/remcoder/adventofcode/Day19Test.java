package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day19Test {
    private Day19 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day19();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(2040, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day19/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(25165632, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day19/input"))));
    }
}