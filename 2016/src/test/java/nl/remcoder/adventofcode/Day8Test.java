package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test {
    private Day8 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day8();
    }

    @Test
    void part1Case1() {
        String data = """
                      rect 3x2
                      rotate column x=1 by 1
                      rotate row y=0 by 4
                      rotate column x=1 by 1
                      """;

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(119, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("ZFHFSFOGPO", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }
}