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
    public void testPart1Case1() {
        String input = """
                       1-3 a: abcde
                       1-3 b: cdefg
                       2-9 c: ccccccccc
                       """;

        assertEquals(2, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(548, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       1-3 a: abcde
                       1-3 b: cdefg
                       2-9 c: ccccccccc
                       """;

        assertEquals(1, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(502, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()))));
    }
}