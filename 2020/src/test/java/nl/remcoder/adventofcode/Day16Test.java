package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {
    private Day16 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day16();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       class: 1-3 or 5-7
                       row: 6-11 or 33-44
                       seat: 13-40 or 45-50
                                       
                       your ticket:
                       7,1,14
                                       
                       nearby tickets:
                       7,3,47
                       40,4,50
                       55,2,20
                       38,6,12
                       """;

        assertEquals(71, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(25895, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2020/day16/input"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(5865723727753L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2020/day16/input"))));
    }
}