package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {

    private Day4 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day4();
    }

    @Test
    public void testPart1Case1() {
        var input = "111111-111111";

        assertEquals(1, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case2() {
        var input = "223450-223450";

        assertEquals(0, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case3() {
        var input = "123789-123789";

        assertEquals(0, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(1246, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2019/day4/input"))));
    }

    @Test
    public void testPart2Case1() {
        var input = "112233-112233";

        assertEquals(1, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case2() {
        var input = "123444-123444";
       
        assertEquals(0, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case3() {
        var input = "111122-111122";
       
        assertEquals(1, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(814, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2019/day4/input"))));

    }
}