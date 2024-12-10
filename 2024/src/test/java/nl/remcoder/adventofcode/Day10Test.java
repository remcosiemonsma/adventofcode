package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {
    private Day10 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day10();
    }

    @Test
    void part1Case1() {
        String data = """
                      0123
                      1234
                      8765
                      9876
                      """;

        assertEquals(1, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      ...0...
                      ...1...
                      ...2...
                      6543456
                      7.....7
                      8.....8
                      9.....9
                      """;

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = """
                      ..90..9
                      ...1.98
                      ...2..7
                      6543456
                      765.987
                      876....
                      987....
                      """;

        assertEquals(4, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = """
                      10..9..
                      2...8..
                      3...7..
                      4567654
                      ...8..3
                      ...9..2
                      .....01
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case5() {
        String data = """
                      89010123
                      78121874
                      87430965
                      96549874
                      45678903
                      32019012
                      01329801
                      10456732
                      """;

        assertEquals(36, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(501, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day10/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      .....0.
                      ..4321.
                      ..5..2.
                      ..6543.
                      ..7..4.
                      ..8765.
                      ..9....
                      """;

        assertEquals(3, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      ..90..9
                      ...1.98
                      ...2..7
                      6543456
                      765.987
                      876....
                      987....
                      """;

        assertEquals(13, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case3() {
        String data = """
                      012345
                      123456
                      234567
                      345678
                      4.6789
                      56789.
                      """;

        assertEquals(227, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case4() {
        String data = """
                      89010123
                      78121874
                      87430965
                      96549874
                      45678903
                      32019012
                      01329801
                      10456732
                      """;

        assertEquals(81, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1017, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day10/input"))));
    }
}