package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    private Day14 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day14();
    }

    @Test
    void part1Case1() {
        String data = """
                      p=0,4 v=3,-3
                      p=6,3 v=-1,-3
                      p=10,3 v=-1,2
                      p=2,0 v=2,-1
                      p=0,0 v=1,3
                      p=3,0 v=-2,-2
                      p=7,6 v=-1,-3
                      p=3,0 v=-1,-2
                      p=9,3 v=2,3
                      p=7,3 v=-1,2
                      p=2,4 v=2,-3
                      p=9,5 v=-3,-3
                      """;

        testSubject.setGridWidth(11);
        testSubject.setGridHeight(7);

        assertEquals(12, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setGridWidth(101);
        testSubject.setGridHeight(103);

        assertEquals(208437768, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day14/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setGridWidth(101);
        testSubject.setGridHeight(103);

        assertEquals(7492, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day14/input"))));
    }
}