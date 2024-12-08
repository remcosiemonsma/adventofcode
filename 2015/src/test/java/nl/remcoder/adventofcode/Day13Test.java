package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Test {
    private Day13 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day13();
    }

    @Test
    public void part1Case1() {
        String data = """
                      Alice would gain 54 happiness units by sitting next to Bob.
                      Alice would lose 79 happiness units by sitting next to Carol.
                      Alice would lose 2 happiness units by sitting next to David.
                      Bob would gain 83 happiness units by sitting next to Alice.
                      Bob would lose 7 happiness units by sitting next to Carol.
                      Bob would lose 63 happiness units by sitting next to David.
                      Carol would lose 62 happiness units by sitting next to Alice.
                      Carol would gain 60 happiness units by sitting next to Bob.
                      Carol would gain 55 happiness units by sitting next to David.
                      David would gain 46 happiness units by sitting next to Alice.
                      David would lose 7 happiness units by sitting next to Bob.
                      David would gain 41 happiness units by sitting next to Carol.""";

        assertEquals(330, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(664, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2015/day13/input"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(640, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2015/day13/input"))));
    }
}