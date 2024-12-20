package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {
    private Day2 testSubject;

    @BeforeEach
    public void beforeEach() {
        testSubject = new Day2();
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(2692315, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2019/day2/input"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(9507, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2019/day2/input"))));
    }
}