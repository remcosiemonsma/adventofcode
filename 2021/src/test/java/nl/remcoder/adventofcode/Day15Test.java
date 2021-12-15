package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {
    private Day15 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day15();
    }

    @Test
    void testPart1Case1() {
        String data = """
                      1163751742
                      1381373672
                      2136511328
                      3694931569
                      7463417111
                      1319128137
                      1359912421
                      3125421639
                      1293138521
                      2311944581
                      """;

        assertEquals(40, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(458, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String data = """
                      1163751742
                      1381373672
                      2136511328
                      3694931569
                      7463417111
                      1319128137
                      1359912421
                      3125421639
                      1293138521
                      2311944581
                      """;

        assertEquals(315, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2800, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }
}