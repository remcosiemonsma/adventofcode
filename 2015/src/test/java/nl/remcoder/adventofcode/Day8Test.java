package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day8Test {
    private Day8 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day8();
    }

    @Test
    public void part1Case1() {
        String data = """
                      ""
                      "abc"
                      "aaa\\"aaa"
                      "\\x27"
                      """;

        assertEquals(12, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(1333, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day8/input"))));
    }

    @Test
    public void part2Case1() {
        String data = """
                      ""
                      "abc"
                      "aaa\\"aaa"
                      "\\x27"
                      """;

        assertEquals(19, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(2046, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day8/input"))));
    }
}