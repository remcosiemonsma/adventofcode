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
                      eedadn
                      drvtee
                      eandsr
                      raavrd
                      atevrs
                      tsrnev
                      sdttsa
                      rasrtv
                      nssdts
                      ntnada
                      svetve
                      tesnvt
                      vntsnd
                      vrdear
                      dvrsen
                      enarar
                      """;

        assertEquals("easter", testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals("qoclwvah", testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day6/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      eedadn
                      drvtee
                      eandsr
                      raavrd
                      atevrs
                      tsrnev
                      sdttsa
                      rasrtv
                      nssdts
                      ntnada
                      svetve
                      tesnvt
                      vntsnd
                      vrdear
                      dvrsen
                      enarar
                      """;

        assertEquals("advent", testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("ryrgviuv", testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day6/input"))));
    }
}