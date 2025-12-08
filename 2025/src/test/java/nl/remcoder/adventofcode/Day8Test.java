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
                      162,817,812
                      57,618,57
                      906,360,560
                      592,479,940
                      352,342,300
                      466,668,158
                      542,29,236
                      431,825,988
                      739,650,466
                      52,470,668
                      216,146,977
                      819,987,18
                      117,168,530
                      805,96,715
                      346,949,466
                      970,615,88
                      941,993,340
                      862,61,35
                      984,92,344
                      425,690,689
                      """;

        assertEquals(40, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setWantedConnections(1000);

        assertEquals(115885, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2025/day8/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      162,817,812
                      57,618,57
                      906,360,560
                      592,479,940
                      352,342,300
                      466,668,158
                      542,29,236
                      431,825,988
                      739,650,466
                      52,470,668
                      216,146,977
                      819,987,18
                      117,168,530
                      805,96,715
                      346,949,466
                      970,615,88
                      941,993,340
                      862,61,35
                      984,92,344
                      425,690,689
                      """;

        assertEquals(25272, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(274150525, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2025/day8/input"))));
    }
}