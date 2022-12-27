package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {
    private Day11 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day11();
    }

    @Test
    void part1Case1() {
        String data = """
                      ne,ne,ne
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      ne,ne,sw,sw
                      """;

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = """
                      ne,ne,s,s
                      """;

        assertEquals(2, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = """
                      se,sw,se,sw,sw
                      """;

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(696, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1461, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI()))));
    }
}