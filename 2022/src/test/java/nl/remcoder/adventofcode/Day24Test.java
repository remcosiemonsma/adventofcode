package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    private Day24 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day24();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       #.######
                       #>>.<^<#
                       #.<..<<#
                       #>v.><>#
                       #<^v^^>#
                       ######.#
                       """;

        assertEquals(18, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(249, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day24/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       #.######
                       #>>.<^<#
                       #.<..<<#
                       #>v.><>#
                       #<^v^^>#
                       ######.#
                       """;

        assertEquals(54, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(735, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day24/input"))));
    }
}