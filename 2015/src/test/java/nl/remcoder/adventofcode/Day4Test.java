package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Test {
    private Day4 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day4();
    }

    @Test
    public void part1Case1() {
        String data = "abcdef";

        assertEquals(609043, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case2() {
        String data = "pqrstuv";

        assertEquals(1048970, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(117946, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day4/input"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(3938038, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day4/input"))));
    }
}