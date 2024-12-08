package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {
    private Day25 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day25();
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals("25166400", testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2019/day25/input"))));
    }
}