package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class Day9Test {
    private Day9 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day9();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(2351176124L, testSubject.handlePart1(Files.lines(Paths.get("../aoc-inputs/2019/day9/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(73110, testSubject.handlePart2(Files.lines(Paths.get("../aoc-inputs/2019/day9/input"))));
    }
}
