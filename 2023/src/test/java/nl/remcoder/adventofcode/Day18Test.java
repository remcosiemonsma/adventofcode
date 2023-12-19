package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
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
                      R 6 (#70c710)
                      D 5 (#0dc571)
                      L 2 (#5713f0)
                      D 2 (#d2c081)
                      R 2 (#59c680)
                      D 2 (#411b91)
                      L 5 (#8ceee2)
                      U 2 (#caa173)
                      L 1 (#1b58a2)
                      U 2 (#caa171)
                      R 2 (#7807d2)
                      U 3 (#a77fa3)
                      L 2 (#015232)
                      U 2 (#7a21e3)
                      """;

        assertEquals(62, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(35244, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()))));
    }

    @Test
    void testPart1Input1() throws Exception {
        assertEquals(1673934, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/aoc-2023-day-18-challenge-1.txt").toURI()))));
    }

    @Test
    void testPart1Input2() throws Exception {
        assertEquals(6585486, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/aoc-2023-day-18-challenge-2.txt").toURI()))));
    }

    @Test
    void testPart1Input3() throws Exception {
        assertEquals(162531699, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/aoc-2023-day-18-challenge-3.txt").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      R 6 (#70c710)
                      D 5 (#0dc571)
                      L 2 (#5713f0)
                      D 2 (#d2c081)
                      R 2 (#59c680)
                      D 2 (#411b91)
                      L 5 (#8ceee2)
                      U 2 (#caa173)
                      L 1 (#1b58a2)
                      U 2 (#caa171)
                      R 2 (#7807d2)
                      U 3 (#a77fa3)
                      L 2 (#015232)
                      U 2 (#7a21e3)
                      """;

        assertEquals(952408144115L, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(85070763635666L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()))));
    }

    @Test
    void testPart2Input1() throws Exception {
        assertEquals(342607851833539243L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/aoc-2023-day-18-challenge-1.txt").toURI()))));
    }

    @Test
    void testPart2Input2() throws Exception {
        assertEquals(1316896843142572432L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/aoc-2023-day-18-challenge-2.txt").toURI()))));
    }

    @Test
    void testPart2Input3() throws Exception {
        assertEquals(new BigInteger("34386825221712537799"), testSubject.handlePart2BigInteger(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/aoc-2023-day-18-challenge-3.txt").toURI()))));
    }
}