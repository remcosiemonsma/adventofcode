package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test {
    private Day18 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day18();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       1 + 2 * 3 + 4 * 5 + 6
                       """;

        assertEquals(71, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case2() {
        String input = """
                       1 + (2 * 3) + (4 * (5 + 6))
                       """;

        assertEquals(51, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case3() {
        String input = """
                       2 * 3 + (4 * 5)
                       """;

        assertEquals(26, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case4() {
        String input = """
                       5 + (8 * 3 + 9 + 3 * 4 * 3)
                       """;

        assertEquals(437, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case5() {
        String input = """
                       5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
                       """;

        assertEquals(12240, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case6() {
        String input = """
                       ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
                       """;

        assertEquals(13632, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(209335026987L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2020/day18/input"))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       1 + 2 * 3 + 4 * 5 + 6
                       """;

        assertEquals(231, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case2() {
        String input = """
                       1 + (2 * 3) + (4 * (5 + 6))
                       """;

        assertEquals(51, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case3() {
        String input = """
                       2 * 3 + (4 * 5)
                       """;

        assertEquals(46, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case4() {
        String input = """
                       5 + (8 * 3 + 9 + 3 * 4 * 3)
                       """;

        assertEquals(1445, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case5() {
        String input = """
                       5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
                       """;

        assertEquals(669060, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case6() {
        String input = """
                       ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
                       """;

        assertEquals(23340, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(33331817392479L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2020/day18/input"))));
    }
}