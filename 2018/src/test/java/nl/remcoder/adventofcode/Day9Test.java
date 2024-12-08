package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day9Test {
    private Day9 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day9();
    }

    @Test
    void part1Case1() {
        String data = """
                      10 players; last marble is worth 1618 points
                      """;

        assertEquals(8317, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      13 players; last marble is worth 7999 points
                      """;

        assertEquals(146373, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = """
                      17 players; last marble is worth 1104 points
                      """;

        assertEquals(2764, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = """
                      21 players; last marble is worth 6111 points
                      """;

        assertEquals(54718, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case5() {
        String data = """
                      30 players; last marble is worth 5807 points
                      """;

        assertEquals(37305, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(418237, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day9/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(3505711612L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day9/input"))));
    }
}