package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {
    private Day25 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day25();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       1=-0-2
                       12111
                       2=0=
                       21
                       2=01
                       111
                       20012
                       112
                       1=-1=
                       1-12
                       12
                       1=
                       122
                       """;

        assertEquals("2=-1=0", testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals("2=-0=01----22-0-1-10", testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day25/input").toURI()))));
    }
}