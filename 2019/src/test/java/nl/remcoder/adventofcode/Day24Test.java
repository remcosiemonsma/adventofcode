package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    private Day24 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day24();
    }

    @Test
    void testPart1Case1() {
        var input = """
                    ....#
                    #..#.
                    #..##
                    ..#..
                    #....
                    """;

        assertEquals(2129920, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(3186366, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        var input = """
                    ....#
                    #..#.
                    #..##
                    ..#..
                    #....
                    """;

        testSubject.setMinutes(10);
        
        assertEquals(99, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setMinutes(200);        

        assertEquals(2031, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI()))));
    }
}