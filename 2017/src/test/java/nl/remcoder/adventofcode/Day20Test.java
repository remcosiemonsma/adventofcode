package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day20Test {
    private Day20 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day20();
    }

    @Test
    void part1Case1() {
        String data = """
                      p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>
                      p=<4,0,0>, v=<0,0,0>, a=<-2,0,0>
                      """;

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(243, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2017/day20/input"))));
    }


    @Test
    void part2Case1() {
        String data = """
                      p=<-6,0,0>, v=<3,0,0>, a=<0,0,0>
                      p=<-4,0,0>, v=<2,0,0>, a=<0,0,0>
                      p=<-2,0,0>, v=<1,0,0>, a=<0,0,0>
                      p=<3,0,0>, v=<-1,0,0>, a=<0,0,0>
                      """;

        assertEquals(1, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(648, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2017/day20/input"))));
    }
}