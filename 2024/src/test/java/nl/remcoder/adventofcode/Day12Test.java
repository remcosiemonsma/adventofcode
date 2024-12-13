package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    private Day12 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day12();
    }

    @Test
    void part1Case1() {
        String data = """
                      AAAA
                      BBCD
                      BBCC
                      EEEC
                      """;

        assertEquals(140, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      OOOOO
                      OXOXO
                      OOOOO
                      OXOXO
                      OOOOO
                      """;

        assertEquals(772, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = """
                      RRRRIICCFF
                      RRRRIICCCF
                      VVRRRCCFFF
                      VVRCCCJFFF
                      VVVVCJJCFE
                      VVIVCCJJEE
                      VVIIICJJEE
                      MIIIIIJJEE
                      MIIISIJEEE
                      MMMISSJEEE
                      """;

        assertEquals(1930, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1465112, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day12/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      AAAA
                      BBCD
                      BBCC
                      EEEC
                      """;

        assertEquals(80, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      OOOOO
                      OXOXO
                      OOOOO
                      OXOXO
                      OOOOO
                      """;

        assertEquals(436, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case3() {
        String data = """
                      EEEEE
                      EXXXX
                      EEEEE
                      EXXXX
                      EEEEE
                      """;

        assertEquals(236, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case4() {
        String data = """
                      AAAAAA
                      AAABBA
                      AAABBA
                      ABBAAA
                      ABBAAA
                      AAAAAA
                      """;

        assertEquals(368, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case5() {
        String data = """
                      RRRRIICCFF
                      RRRRIICCCF
                      VVRRRCCFFF
                      VVRCCCJFFF
                      VVVVCJJCFE
                      VVIVCCJJEE
                      VVIIICJJEE
                      MIIIIIJJEE
                      MIIISIJEEE
                      MMMISSJEEE
                      """;

        assertEquals(1206, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        //883873 too low
        assertEquals(893790, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day12/input"))));
    }
}