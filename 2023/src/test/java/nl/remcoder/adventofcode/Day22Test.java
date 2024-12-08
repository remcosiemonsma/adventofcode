package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day22Test {
    private Day22 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day22();
    }

    @Test
    void part1Case1() {
        String data = """
                      1,0,1~1,2,1
                      0,0,2~2,0,2
                      0,2,3~2,2,3
                      0,0,4~0,2,4
                      2,0,5~2,2,5
                      0,1,6~2,1,6
                      1,1,8~1,1,9
                      """;

        assertEquals(5, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(515, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day22/input"))));
    }

    @Test
    void part1Case2() {
        String data = """
                      1,0,1~1,2,1
                      0,0,2~2,0,2
                      0,2,3~2,2,3
                      0,0,4~0,2,4
                      2,0,5~2,2,5
                      0,1,6~2,1,6
                      1,1,8~1,1,9
                      """;

        assertEquals(7, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(101541, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day22/input"))));
    }
}