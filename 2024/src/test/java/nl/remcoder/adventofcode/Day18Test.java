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
    void part1Case1() {
        String data = """
                      5,4
                      4,2
                      4,5
                      3,0
                      2,1
                      6,3
                      2,4
                      1,5
                      0,6
                      3,3
                      2,6
                      5,1
                      1,2
                      5,5
                      2,5
                      6,5
                      1,4
                      0,4
                      6,4
                      1,1
                      6,1
                      1,0
                      0,5
                      1,6
                      2,0
                      """;

        testSubject.setBlocksToCatch(12);
        testSubject.setHeight(6);
        testSubject.setWidth(6);

        assertEquals(22, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setBlocksToCatch(1024);
        testSubject.setHeight(70);
        testSubject.setWidth(70);

        assertEquals(262, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day18/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      5,4
                      4,2
                      4,5
                      3,0
                      2,1
                      6,3
                      2,4
                      1,5
                      0,6
                      3,3
                      2,6
                      5,1
                      1,2
                      5,5
                      2,5
                      6,5
                      1,4
                      0,4
                      6,4
                      1,1
                      6,1
                      1,0
                      0,5
                      1,6
                      2,0
                      """;

        testSubject.setBlocksToCatch(12);
        testSubject.setHeight(6);
        testSubject.setWidth(6);

        assertEquals("6,1", testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setBlocksToCatch(1024);
        testSubject.setHeight(70);
        testSubject.setWidth(70);

        assertEquals("22,20", testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day18/input"))));
    }
}