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
                      {}
                      """;

        assertEquals(1, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      {{{}}}
                      """;

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = """
                      {{},{}}
                      """;

        assertEquals(5, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = """
                      {{{},{},{{}}}}
                      """;

        assertEquals(16, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case5() {
        String data = """
                      {<a>,<a>,<a>,<a>}
                      """;

        assertEquals(1, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case6() {
        String data = """
                      {{<ab>},{<ab>},{<ab>},{<ab>}}
                      """;

        assertEquals(9, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case7() {
        String data = """
                      {{<!!>},{<!!>},{<!!>},{<!!>}}
                      """;

        assertEquals(9, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case8() {
        String data = """
                      {{<a!>},{<a!>},{<a!>},{<ab>}}
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(7640, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day9/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      <>
                      """;

        assertEquals(0, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      <random characters>
                      """;

        assertEquals(17, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case3() {
        String data = """
                      <<<<>
                      """;

        assertEquals(3, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case4() {
        String data = """
                      <{!>}>
                      """;

        assertEquals(2, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case5() {
        String data = """
                      <!!>
                      """;

        assertEquals(0, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case6() {
        String data = """
                      <!!!>>
                      """;

        assertEquals(0, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case7() {
        String data = """
                      <{o"i!a,<{i<a>
                      """;

        assertEquals(10, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(4368, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2017/day9/input"))));
    }
}