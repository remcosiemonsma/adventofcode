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
                      0:
                      ###
                      ##.
                      ##.
                      
                      1:
                      ###
                      ##.
                      .##
                      
                      2:
                      .##
                      ###
                      ##.
                      
                      3:
                      ##.
                      ###
                      ##.
                      
                      4:
                      ###
                      #..
                      ###
                      
                      5:
                      ###
                      .#.
                      ###
                      
                      4x4: 0 0 0 0 2 0
                      12x5: 1 0 1 0 2 2
                      12x5: 1 0 1 0 3 2
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(414, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2025/day12/input"))));
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

        assertEquals(0, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(0, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2025/day12/input"))));
    }
}