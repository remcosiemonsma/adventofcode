package nl.remcoder.adventofcode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {
    private Day1 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day1();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       1000
                       2000
                       3000
                                       
                       4000
                                       
                       5000
                       6000
                                       
                       7000
                       8000
                       9000
                                       
                       10000
                       """;

        assertEquals(24000, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(71934, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       1000
                       2000
                       3000
                                       
                       4000
                                       
                       5000
                       6000
                                       
                       7000
                       8000
                       9000
                                       
                       10000
                       """;

        assertEquals(45000, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(211447, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))));
    }
}