package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class Day3Test {
    private Day3 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day3();
    }

    @Test
    public void part1Case1() {
        String data = "5 10 25";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(869, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = """
                      101 301 501
                      102 302 502
                      103 303 503
                      201 401 601
                      202 402 602
                      203 403 603
                      """;

        assertEquals(6, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(1544, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))));
    }
}