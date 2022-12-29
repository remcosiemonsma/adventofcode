package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {
    private Day17 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day17();
    }

    @Test
    void part1Case1() {
        String data = """
                      x=495, y=2..7
                      y=7, x=495..501
                      x=501, y=3..7
                      x=498, y=2..4
                      x=506, y=1..2
                      x=498, y=10..13
                      x=504, y=10..13
                      y=13, x=498..504
                      """;

        assertEquals(57, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(38409, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      x=495, y=2..7
                      y=7, x=495..501
                      x=501, y=3..7
                      x=498, y=2..4
                      x=506, y=1..2
                      x=498, y=10..13
                      x=504, y=10..13
                      y=13, x=498..504
                      """;

        assertEquals(29, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(32288, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()))));
    }
}