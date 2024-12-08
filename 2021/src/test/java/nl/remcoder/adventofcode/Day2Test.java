package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {

    private Day2 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day2();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       forward 5
                       down 5
                       forward 8
                       up 3
                       down 8
                       forward 2
                       """;

        assertEquals(150, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(2215080, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2021/day2/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       forward 5
                       down 5
                       forward 8
                       up 3
                       down 8
                       forward 2
                       """;

        assertEquals(900, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1864715580, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2021/day2/input"))));
    }
}