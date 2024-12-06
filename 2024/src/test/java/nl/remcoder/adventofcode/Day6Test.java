package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {
    private Day6 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day6();
    }

    @Test
    void part1Case1() {
        String data = """
                      ....#.....
                      .........#
                      ..........
                      ..#.......
                      .......#..
                      ..........
                      .#..^.....
                      ........#.
                      #.........
                      ......#...
                      """;

        assertEquals(41, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(4433, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      ....#.....
                      .........#
                      ..........
                      ..#.......
                      .......#..
                      ..........
                      .#..^.....
                      ........#.
                      #.........
                      ......#...
                      """;

        assertEquals(6, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1516, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()))));
    }
}