package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {
    private Day13 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day13();
    }

    @Test
    void part1Case1() {
        String data = """
                      Button A: X+94, Y+34
                      Button B: X+22, Y+67
                      Prize: X=8400, Y=5400
                      
                      Button A: X+26, Y+66
                      Button B: X+67, Y+21
                      Prize: X=12748, Y=12176
                      
                      Button A: X+17, Y+86
                      Button B: X+84, Y+37
                      Prize: X=7870, Y=6450
                      
                      Button A: X+69, Y+23
                      Button B: X+27, Y+71
                      Prize: X=18641, Y=10279
                      """;

        assertEquals(480, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(36571, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day13/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      Button A: X+94, Y+34
                      Button B: X+22, Y+67
                      Prize: X=8400, Y=5400
                      
                      Button A: X+26, Y+66
                      Button B: X+67, Y+21
                      Prize: X=12748, Y=12176
                      
                      Button A: X+17, Y+86
                      Button B: X+84, Y+37
                      Prize: X=7870, Y=6450
                      
                      Button A: X+69, Y+23
                      Button B: X+27, Y+71
                      Prize: X=18641, Y=10279
                      """;

        assertEquals(875318608908L, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(85527711500010L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day13/input"))));
    }
}