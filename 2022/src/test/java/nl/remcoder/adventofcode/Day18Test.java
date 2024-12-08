package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test {
    private Day18 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day18();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       1,1,1
                       2,1,1
                       """;

        assertEquals(10, testSubject.handlePart1(input.lines()));
    }
    
    @Test
    void testPart1Case2() {
        String input = """
                       2,2,2
                       1,2,2
                       3,2,2
                       2,1,2
                       2,3,2
                       2,2,1
                       2,2,3
                       2,2,4
                       2,2,6
                       1,2,5
                       3,2,5
                       2,1,5
                       2,3,5
                       """;

        assertEquals(64, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(3564, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day18/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       2,2,2
                       1,2,2
                       3,2,2
                       2,1,2
                       2,3,2
                       2,2,1
                       2,2,3
                       2,2,4
                       2,2,6
                       1,2,5
                       3,2,5
                       2,1,5
                       2,3,5
                       """;

        assertEquals(58, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2106, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day18/input"))));
    }
}