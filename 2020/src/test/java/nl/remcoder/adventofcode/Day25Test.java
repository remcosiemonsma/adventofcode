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
    public void testPart1Case1() {
        String input = """
                       5764801
                       17807724
                       """;

        assertEquals(14897079, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(11707042, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2020/day25/input"))));
    }
}