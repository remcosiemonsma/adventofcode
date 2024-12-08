package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day23Test {
    private Day23 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day23();
    }

    @Test
    public void testPart1Case1() {
        String input = "389125467";

        testSubject.setMoves(10);
        
        assertEquals(92658374, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case2() {
        String input = "389125467";

        testSubject.setMoves(100);
        
        assertEquals(67384529, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        testSubject.setMoves(100);
        
        assertEquals(76952348, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2020/day23/input"))));
    }

    @Test
    public void testPart2Case1() {
        String input = "389125467";

        assertEquals(149245887792L, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(72772522064L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2020/day23/input"))));
    }
}