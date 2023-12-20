package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day20Test {
    private Day20 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day20();
    }

    @Test
    void part1Case1() {
        String data = """
                      broadcaster -> a, b, c
                      %a -> b
                      %b -> c
                      %c -> inv
                      &inv -> a
                      """;

        assertEquals(32000000, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      broadcaster -> a
                      %a -> inv, con
                      &inv -> b
                      %b -> con
                      &con -> output
                      """;

        assertEquals(11687500, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(925955316, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day20/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(241528477694627L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day20/input").toURI()))));
    }
}