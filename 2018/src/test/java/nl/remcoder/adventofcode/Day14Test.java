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
                      9
                      """;

        assertEquals("5158916779", testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      5
                      """;

        assertEquals("0124515891", testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = """
                      18
                      """;

        assertEquals("9251071085", testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = """
                      2018
                      """;

        assertEquals("5941429882", testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals("5115114101", testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day14/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      51589
                      """;

        assertEquals(9, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      01245
                      """;

        assertEquals(5, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case3() {
        String data = """
                      92510
                      """;

        assertEquals(18, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case4() {
        String data = """
                      59414
                      """;

        assertEquals(2018, testSubject.handlePart2(data.lines()));
    }
    
    @Test
    void testPart2Input() throws Exception {
        assertEquals(20310465, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day14/input"))));
    }
}