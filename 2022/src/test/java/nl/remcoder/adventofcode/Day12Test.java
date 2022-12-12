package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    private Day12 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day12();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       Sabqponm
                       abcryxxl
                       accszExk
                       acctuvwj
                       abdefghi
                       """;

        assertEquals(31, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(462, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       Sabqponm
                       abcryxxl
                       accszExk
                       acctuvwj
                       abdefghi
                       """;

        assertEquals(29, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(451, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))));
    }
}