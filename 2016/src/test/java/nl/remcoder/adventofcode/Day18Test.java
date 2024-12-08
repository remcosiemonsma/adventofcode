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
                      ..^^.
                      """;
        
        testSubject.setRows(3);

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      .^^.^.^^^^
                      """;

        testSubject.setRows(10);
        
        assertEquals(38, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setRows(40);
        
        assertEquals(1939, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day18/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(19999535, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day18/input"))));
    }
}