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
    public void testPart1Case1() {
        var input = """
                    R8,U5,L5,D3
                    U7,R6,D4,L4
                    """;
        
        assertEquals(6, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case2() {
        var input = """
                    R75,D30,R83,U83,L12,D49,R71,U7,L72
                    U62,R66,U55,R34,D71,R55,D58,R83
                    """;

        assertEquals(159, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case3() {
        var input = """
                    R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
                    U98,R91,D20,R16,D67,R40,U7,R15,U6,R7
                    """;

        assertEquals(135, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(865, testSubject.handlePart1(Files.lines(Paths.get("../aoc-inputs/2019/day3/input"))));
    }

    @Test
    public void testPart2Case1() {
        var input = """
                    R8,U5,L5,D3
                    U7,R6,D4,L4
                    """;
        
        assertEquals(30, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case2() {
        var input = """
                    R75,D30,R83,U83,L12,D49,R71,U7,L72
                    U62,R66,U55,R34,D71,R55,D58,R83
                    """;

        assertEquals(610, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case3() {
        var input = """
                    R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
                    U98,R91,D20,R16,D67,R40,U7,R15,U6,R7
                    """;

        assertEquals(410, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(35038, testSubject.handlePart2(Files.lines(Paths.get("../aoc-inputs/2019/day3/input"))));
    }
}