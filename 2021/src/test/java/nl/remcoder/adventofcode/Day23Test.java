package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day23Test {
    private Day23 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day23();
    }

    @Test
    @Disabled("No code implementation ready yet")
    void testPart1Case1() {
        String input = """
                       #############
                       #...........#
                       ###B#C#B#D###
                         #A#D#C#A#
                         #########
                       """;

        assertEquals(12521, testSubject.handlePart1(input.lines()));
    }

    @Test
    @Disabled("No code implementation ready yet")
    void testPart1Input() throws Exception {
        assertEquals(19019, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }

    @Test
    @Disabled("No code implementation ready yet")
    void testPart2Case1() {
        String input = """
                       #############
                       #...........#
                       ###B#C#B#D###
                         #A#D#C#A#
                         #########
                       """;

        assertEquals(44169, testSubject.handlePart2(input.lines()));
    }

    @Test
    @Disabled("No code implementation ready yet")
    void testPart2Input() throws Exception {
        assertEquals(47533, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }
}