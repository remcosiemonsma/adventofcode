package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {
    private Day13 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day13();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       []
                       [3]
                       """;

        assertEquals(1, testSubject.handlePart1(input.lines()));
    } 
    
    @Test
    void testPart1Case2() {
        String input = """
                       [1,1,3,1,1]
                       [1,1,5,1,1]
                       
                       [[1],[2,3,4]]
                       [[1],4]
                       
                       [9]
                       [[8,7,6]]
                       
                       [[4,4],4,4]
                       [[4,4],4,4,4]
                       
                       [7,7,7,7]
                       [7,7,7]
                       
                       []
                       [3]
                       
                       [[[]]]
                       [[]]
                       
                       [1,[2,[3,[4,[5,6,7]]]],8,9]
                       [1,[2,[3,[4,[5,6,0]]]],8,9]
                       """;

        assertEquals(13, testSubject.handlePart1(input.lines()));
    }
    @Test
    void testPart1Input() throws Exception {
        assertEquals(5675, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day13/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       [1,1,3,1,1]
                       [1,1,5,1,1]
                       
                       [[1],[2,3,4]]
                       [[1],4]
                       
                       [9]
                       [[8,7,6]]
                       
                       [[4,4],4,4]
                       [[4,4],4,4,4]
                       
                       [7,7,7,7]
                       [7,7,7]
                       
                       []
                       [3]
                       
                       [[[]]]
                       [[]]
                       
                       [1,[2,[3,[4,[5,6,7]]]],8,9]
                       [1,[2,[3,[4,[5,6,0]]]],8,9]
                       """;

        assertEquals(140, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(20383, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day13/input"))));
    }
}