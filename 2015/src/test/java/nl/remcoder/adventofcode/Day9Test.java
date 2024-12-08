package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Test {
    private Day9 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day9();
    }

    @Test
    public void part1Case1() {
        String data = """
                      London to Dublin = 464
                      London to Belfast = 518
                      Dublin to Belfast = 141
                      """;

        assertEquals(605, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(141, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day9/input"))));
    }

    @Test
    public void part2Case1() {
        String data = """
                      London to Dublin = 464
                      London to Belfast = 518
                      Dublin to Belfast = 141
                      """;

        assertEquals(982, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(736, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day9/input"))));
    }
}