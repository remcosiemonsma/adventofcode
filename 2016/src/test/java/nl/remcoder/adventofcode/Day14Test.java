package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    private Day14 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day14();
    }

    @Test
    void part1Case1() throws Exception {
        String data = "abc";

        assertEquals(22728, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(25427, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day14/input").toURI()))));
    }

    @Test
    void part2Case1() throws Exception {
        String data = "abc";

        assertEquals(22551, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(22045, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day14/input").toURI()))));
    }
}