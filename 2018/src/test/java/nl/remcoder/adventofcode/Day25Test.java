package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {
    private Day25 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day25();
    }

    @Test
    void part1Case1() {
        String data = """
                      0,0,0,0
                      3,0,0,0
                      0,3,0,0
                      0,0,3,0
                      0,0,0,3
                      0,0,0,6
                      9,0,0,0
                      12,0,0,0
                      """;

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      -1,2,2,0
                      0,0,2,-2
                      0,0,0,-2
                      -1,2,0,0
                      -2,-2,-2,2
                      3,0,2,-1
                      -1,3,2,2
                      -1,0,-1,0
                      0,2,1,-2
                      3,0,0,0
                      """;

        assertEquals(4, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = """
                      1,-1,0,1
                      2,0,-1,0
                      3,2,-1,0
                      0,0,3,1
                      0,0,-1,-1
                      2,3,-2,0
                      -2,2,0,0
                      2,-2,0,-1
                      1,-1,0,-1
                      3,2,0,2
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = """
                      1,-1,-1,-2
                      -2,-2,0,1
                      0,2,1,3
                      -2,3,-2,1
                      0,2,3,-2
                      -1,-1,1,-2
                      0,-2,-1,0
                      -2,2,3,-1
                      1,2,2,0
                      -1,-2,0,-2
                      """;

        assertEquals(8, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(346, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day25/input").toURI()))));
    }
}