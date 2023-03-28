package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {
    private Day15 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day15();
    }

    @Test
    void part1Case1() {
        String data = """
                      #######
                      #.G...#
                      #...EG#
                      #.#.#G#
                      #..G#E#
                      #.....#
                      #######
                      """;

        assertEquals(27730, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      #######
                      #G..#E#
                      #E#E.E#
                      #G.##.#
                      #...#E#
                      #...E.#
                      #######
                      """;

        assertEquals(36334, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = """
                      #######
                      #E..EG#
                      #.#G.E#
                      #E.##E#
                      #G..#.#
                      #..E#.#
                      #######
                      """;

        assertEquals(39514, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = """
                      #######
                      #E.G#.#
                      #.#G..#
                      #G.#.G#
                      #G..#.#
                      #...E.#
                      #######
                      """;

        assertEquals(27755, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case5() {
        String data = """
                      #######
                      #.E...#
                      #.#..G#
                      #.###.#
                      #E#G#G#
                      #...#G#
                      #######
                      """;

        assertEquals(28944, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case6() {
        String data = """
                      #########
                      #G......#
                      #.E.#...#
                      #..##..G#
                      #...##..#
                      #...#...#
                      #.G...G.#
                      #.....G.#
                      #########
                      """;

        assertEquals(18740, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(221754, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      #######
                      #.G...#
                      #...EG#
                      #.#.#G#
                      #..G#E#
                      #.....#
                      #######
                      """;

        assertEquals(4988, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      #######
                      #E..EG#
                      #.#G.E#
                      #E.##E#
                      #G..#.#
                      #..E#.#
                      #######
                      """;

        assertEquals(31284, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case3() {
        String data = """
                      #######
                      #E.G#.#
                      #.#G..#
                      #G.#.G#
                      #G..#.#
                      #...E.#
                      #######
                      """;

        assertEquals(3478, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case4() {
        String data = """
                      #######
                      #.E...#
                      #.#..G#
                      #.###.#
                      #E#G#G#
                      #...#G#
                      #######
                      """;

        assertEquals(6474, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case5() {
        String data = """
                      #########
                      #G......#
                      #.E.#...#
                      #..##..G#
                      #...##..#
                      #...#...#
                      #.G...G.#
                      #.....G.#
                      #########
                      """;

        assertEquals(1140, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(41972, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }
}