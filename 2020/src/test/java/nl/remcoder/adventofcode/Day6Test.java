package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {
    private Day6 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day6();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       abc
                                       
                       a
                       b
                       c
                                       
                       ab
                       ac
                                       
                       a
                       a
                       a
                       a
                                       
                       b
                       """;

        assertEquals(11, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(6799, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2020/day6/input"))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       abc
                                       
                       a
                       b
                       c
                                       
                       ab
                       ac
                                       
                       a
                       a
                       a
                       a
                                       
                       b
                       """;

        assertEquals(6, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(3354, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2020/day6/input"))));
    }
}