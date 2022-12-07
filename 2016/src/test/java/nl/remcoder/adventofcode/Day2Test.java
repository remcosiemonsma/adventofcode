package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class Day2Test {
    private Day2 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day2();
    }

    @Test
    public void part1Case1() {
        String data = """
                      ULL
                      RRDDD
                      LURDL
                      UUUUD
                      """;

        assertEquals("1985", testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals("45973", testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = """
                      ULL
                      RRDDD
                      LURDL
                      UUUUD
                      """;

        assertEquals("5DB3", testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals("27CA4", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()))));
    }
}