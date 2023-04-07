package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day21Test {
    private Day21 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day21();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       Player 1 starting position: 4
                       Player 2 starting position: 8
                       """;

        assertEquals(739785, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(921585, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day21/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       Player 1 starting position: 4
                       Player 2 starting position: 8
                       """;

        assertEquals(444356092776315L, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(911090395997650L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day21/input").toURI()))));
    }
}