package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    private Day12 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day12();
    }

    @Test
    void part1Case1() {
        String data = """
                      cpy 41 a
                      inc a
                      inc a
                      dec a
                      jnz a 2
                      dec a
                      """;

        assertEquals(42, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(318117, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(9227771, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))));
    }
}