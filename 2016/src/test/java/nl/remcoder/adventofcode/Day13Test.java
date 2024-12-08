package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.model.Coordinate;
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
                      10
                      """;
        
        testSubject.setDesiredPosition(new Coordinate(7, 4));

        assertEquals(11, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setDesiredPosition(new Coordinate(31, 39));
        
        assertEquals(90, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2016/day13/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(135, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2016/day13/input"))));
    }
}