package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test {
    private Day14 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day14();
    }

    @Test
    public void part1Case1() {
        String data = """
                      Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                      Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
                      """;

        assertEquals(2660, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(2640, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day14/input"))));
    }

    @Test
    public void part2Case1() {
        String data = """
                      Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                      Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
                      """;

        assertEquals(1564, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(1102, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day14/input"))));
    }
}