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
    void part1Case1() throws Exception {
        String data = """
                      10000
                      """;

        testSubject.setLength(20);
        
        assertEquals("01100", testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setLength(272);
        
        assertEquals("11100110111101110", testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day16/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setLength(35651584);
        
        assertEquals("10001101010000101", testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day16/input"))));
    }
}