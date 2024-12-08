package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {
    private Day16 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day16();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
                       Valve BB has flow rate=13; tunnels lead to valves CC, AA
                       Valve CC has flow rate=2; tunnels lead to valves DD, BB
                       Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
                       Valve EE has flow rate=3; tunnels lead to valves FF, DD
                       Valve FF has flow rate=0; tunnels lead to valves EE, GG
                       Valve GG has flow rate=0; tunnels lead to valves FF, HH
                       Valve HH has flow rate=22; tunnel leads to valve GG
                       Valve II has flow rate=0; tunnels lead to valves AA, JJ
                       Valve JJ has flow rate=21; tunnel leads to valve II
                       """;
        
        assertEquals(1651, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1862, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day16/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
                       Valve BB has flow rate=13; tunnels lead to valves CC, AA
                       Valve CC has flow rate=2; tunnels lead to valves DD, BB
                       Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
                       Valve EE has flow rate=3; tunnels lead to valves FF, DD
                       Valve FF has flow rate=0; tunnels lead to valves EE, GG
                       Valve GG has flow rate=0; tunnels lead to valves FF, HH
                       Valve HH has flow rate=22; tunnel leads to valve GG
                       Valve II has flow rate=0; tunnels lead to valves AA, JJ
                       Valve JJ has flow rate=21; tunnel leads to valve II
                       """;
        
        assertEquals(1707, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2422, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day16/input"))));
    }
}