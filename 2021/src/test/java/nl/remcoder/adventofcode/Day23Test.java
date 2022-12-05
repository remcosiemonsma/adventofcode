package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day23Test {
    private Day23 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day23();
    }

    @Test
    void testPart1Case1() {
        String data = """
                      #############
                      #...........#
                      ###B#C#B#D###
                        #A#D#C#A#
                        #########
                      """;

        assertEquals(12521, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(19019, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String data = """
                      Player 1 starting position: 4
                      Player 2 starting position: 8
                      """;

        assertEquals(444356092776315L, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(911090395997650L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }
}