package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}