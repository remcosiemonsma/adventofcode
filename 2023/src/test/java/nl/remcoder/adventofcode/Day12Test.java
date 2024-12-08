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
                      ???.### 1,1,3
                      .??..??...?##. 1,1,3
                      ?#?#?#?#?#?#?#? 1,3,1,6
                      ????.#...#... 4,1,1
                      ????.######..#####. 1,6,5
                      ?###???????? 3,2,1
                      """;

        assertEquals(21, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      .##.?#??.#.?# 2,1,1,1
                      """;

        assertEquals(1, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = """
                      ???.### 1,1,3
                      """;

        assertEquals(1, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = """
                      ????.######..#####. 1,6,5
                      """;

        assertEquals(4, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(7173, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day12/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      ???.### 1,1,3
                      .??..??...?##. 1,1,3
                      ?#?#?#?#?#?#?#? 1,3,1,6
                      ????.#...#... 4,1,1
                      ????.######..#####. 1,6,5
                      ?###???????? 3,2,1
                      """;

        assertEquals(525152, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(29826669191291L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day12/input"))));
    }
}