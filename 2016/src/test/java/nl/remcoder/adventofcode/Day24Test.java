package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    private Day24 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day24();
    }
    
    @Test
    void part1Case1() {
        String data = """
                      ###########
                      #0.1.....2#
                      #.#######.#
                      #4.......3#
                      ###########
                      """;

        testSubject.setExpectedSize(5);

        assertEquals(14, testSubject.handlePart1(data.lines()));
    }
    
    @Test
    void testPart1Input() throws Exception {
        testSubject.setExpectedSize(8);
        
        assertEquals(442, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day24/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(660, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day24/input"))));
    }
}