package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {
    private Day2 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day2();
    }

    @Test
    void part1Case1() {
        String data = """
                      ULL
                      RRDDD
                      LURDL
                      UUUUD
                      """;

        assertEquals("1985", testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals("45973", testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      ULL
                      RRDDD
                      LURDL
                      UUUUD
                      """;

        assertEquals("5DB3", testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("27CA4", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()))));
    }
}