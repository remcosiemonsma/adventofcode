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
                      aaa: you hhh
                      you: bbb ccc
                      bbb: ddd eee
                      ccc: ddd eee fff
                      ddd: ggg
                      eee: out
                      fff: out
                      ggg: out
                      hhh: ccc fff iii
                      iii: out
                      """;

        assertEquals(5, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(699, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2025/day11/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      svr: aaa bbb
                      aaa: fft
                      fft: ccc
                      bbb: tty
                      tty: ccc
                      ccc: ddd eee
                      ddd: hub
                      hub: fff
                      eee: dac
                      dac: fff
                      fff: ggg hhh
                      ggg: out
                      hhh: out
                      """;

        assertEquals(2, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(388893655378800L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2025/day11/input"))));
    }
}