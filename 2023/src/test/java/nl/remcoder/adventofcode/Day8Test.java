package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test {
    private Day8 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day8();
    }

    @Test
    void part1Case1() {
        String data = """
                      RL
                      
                      AAA = (BBB, CCC)
                      BBB = (DDD, EEE)
                      CCC = (ZZZ, GGG)
                      DDD = (DDD, DDD)
                      EEE = (EEE, EEE)
                      GGG = (GGG, GGG)
                      ZZZ = (ZZZ, ZZZ)
                      """;

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      LLR
                      
                      AAA = (BBB, BBB)
                      BBB = (AAA, ZZZ)
                      ZZZ = (ZZZ, ZZZ)
                      """;

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(15871, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      LR
                      
                      11A = (11B, XXX)
                      11B = (XXX, 11Z)
                      11Z = (11B, XXX)
                      22A = (22B, XXX)
                      22B = (22C, 22C)
                      22C = (22Z, 22Z)
                      22Z = (22B, 22B)
                      XXX = (XXX, XXX)
                      """;

        assertEquals(6, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(11283670395017L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }
}