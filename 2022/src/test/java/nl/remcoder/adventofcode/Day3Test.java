package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {
    private Day3 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day3();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       vJrwpWtwJgWrhcsFMMfFFhFp
                       jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                       PmmdzqPrVvPwwTWBwg
                       wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                       ttgJtRGJQctTZtZT
                       CrZsJsPPZsGzwwsLwLmpwMDw
                       """;

        assertEquals(157, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(7845, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day3/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       vJrwpWtwJgWrhcsFMMfFFhFp
                       jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                       PmmdzqPrVvPwwTWBwg
                       wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                       ttgJtRGJQctTZtZT
                       CrZsJsPPZsGzwwsLwLmpwMDw
                       """;

        assertEquals(70, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2790, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day3/input"))));
    }
}