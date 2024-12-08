package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {
    private Day7 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day7();
    }

    @Test
    void part1Case1() {
        String data = """
                      Step C must be finished before stepCrucible A can begin.
                      Step C must be finished before stepCrucible F can begin.
                      Step A must be finished before stepCrucible B can begin.
                      Step A must be finished before stepCrucible D can begin.
                      Step B must be finished before stepCrucible E can begin.
                      Step D must be finished before stepCrucible E can begin.
                      Step F must be finished before stepCrucible E can begin.
                      """;

        assertEquals("CABDFE", testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals("OCPUEFIXHRGWDZABTQJYMNKVSL", testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2018/day7/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      Step C must be finished before stepCrucible A can begin.
                      Step C must be finished before stepCrucible F can begin.
                      Step A must be finished before stepCrucible B can begin.
                      Step A must be finished before stepCrucible D can begin.
                      Step B must be finished before stepCrucible E can begin.
                      Step D must be finished before stepCrucible E can begin.
                      Step F must be finished before stepCrucible E can begin.
                      """;
        
        testSubject.setAmountOfWorkers(2);
        Day7.setSecondsToAdd(1);

        assertEquals(15, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setAmountOfWorkers(5);
        Day7.setSecondsToAdd(60);

        assertEquals(991, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2018/day7/input"))));
    }
}