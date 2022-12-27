package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {
    private Day15 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day15();
    }

    @Test
    void part1Case1() {
        String data = """
                      Generator A starts with 65
                      Generator B starts with 8921
                      """;

        assertEquals(588, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(577, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      Generator A starts with 65
                      Generator B starts with 8921
                      """;

        assertEquals(309, testSubject.handlePart2(data.lines()));
    }
    @Test
    void testPart2Input() throws Exception {
        assertEquals(316, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }
}