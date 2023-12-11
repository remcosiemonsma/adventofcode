package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {
    private Day11 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day11();
    }

    @Test
    void part1Case1() {
        String data = """
                      ...#......
                      .......#..
                      #.........
                      ..........
                      ......#...
                      .#........
                      .........#
                      ..........
                      .......#..
                      #...#.....
                      """;

        assertEquals(374, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(10292708, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI()))));
    }

    @Test
    void part2Case0() {
        String data = """
                      ...#......
                      .......#..
                      #.........
                      ..........
                      ......#...
                      .#........
                      .........#
                      ..........
                      .......#..
                      #...#.....
                      """;

        testSubject.setExpansion(2);

        assertEquals(374, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case1() {
        String data = """
                      ...#......
                      .......#..
                      #.........
                      ..........
                      ......#...
                      .#........
                      .........#
                      ..........
                      .......#..
                      #...#.....
                      """;

        testSubject.setExpansion(10);

        assertEquals(1030, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      ...#......
                      .......#..
                      #.........
                      ..........
                      ......#...
                      .#........
                      .........#
                      ..........
                      .......#..
                      #...#.....
                      """;

        testSubject.setExpansion(100);

        assertEquals(8410, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setExpansion(1000000);

        assertEquals(790194712336L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI()))));
    }
}