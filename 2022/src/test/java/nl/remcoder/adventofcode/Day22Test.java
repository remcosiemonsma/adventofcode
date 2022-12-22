package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day22Test {
    private Day22 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day22();
    }

    @Test
    void testPart1Case1() {
        String input = """
                               ...#
                               .#..
                               #...
                               ....
                       ...#.......#
                       ........#...
                       ..#....#....
                       ..........#.
                               ...#....
                               .....#..
                               .#......
                               ......#.
                                              
                       10R5L5R10L4R5L5
                       """;

        assertEquals(6032, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1484, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI()))));
    }

    @Disabled("Current solution is hardcoded, maybe if I ever want to make a generic solution I can enable this test")
    @Test
    void testPart2Case1() {
        String input = """
                               ...#
                               .#..
                               #...
                               ....
                       ...#.......#
                       ........#...
                       ..#....#....
                       ..........#.
                               ...#....
                               .....#..
                               .#......
                               ......#.
                                              
                       10R5L5R10L4R5L5
                       """;

        assertEquals(5031, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(142228, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI()))));
    }
}