package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day19Test {
    private Day19 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day19();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       H => HO
                       H => OH
                       O => HH
                                       
                       HOH
                       """;

        assertEquals(4, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        String input = """
                       H => HO
                       H => OH
                       O => HH
                                       
                       HOHOHO
                       """;

        assertEquals(7, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(509, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day19/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(195, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day19/input"))));
    }
}