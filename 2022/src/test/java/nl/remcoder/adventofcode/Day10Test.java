package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {
    private Day10 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day10();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       addx 15
                       addx -11
                       addx 6
                       addx -3
                       addx 5
                       addx -1
                       addx -8
                       addx 13
                       addx 4
                       noop
                       addx -1
                       addx 5
                       addx -1
                       addx 5
                       addx -1
                       addx 5
                       addx -1
                       addx 5
                       addx -1
                       addx -35
                       addx 1
                       addx 24
                       addx -19
                       addx 1
                       addx 16
                       addx -11
                       noop
                       noop
                       addx 21
                       addx -15
                       noop
                       noop
                       addx -3
                       addx 9
                       addx 1
                       addx -3
                       addx 8
                       addx 1
                       addx 5
                       noop
                       noop
                       noop
                       noop
                       noop
                       addx -36
                       noop
                       addx 1
                       addx 7
                       noop
                       noop
                       noop
                       addx 2
                       addx 6
                       noop
                       noop
                       noop
                       noop
                       noop
                       addx 1
                       noop
                       noop
                       addx 7
                       addx 1
                       noop
                       addx -13
                       addx 13
                       addx 7
                       noop
                       addx 1
                       addx -33
                       noop
                       noop
                       noop
                       addx 2
                       noop
                       noop
                       noop
                       addx 8
                       noop
                       addx -1
                       addx 2
                       addx 1
                       noop
                       addx 17
                       addx -9
                       addx 1
                       addx 1
                       addx -3
                       addx 11
                       noop
                       noop
                       addx 1
                       noop
                       addx 1
                       noop
                       noop
                       addx -13
                       addx -19
                       addx 1
                       addx 3
                       addx 26
                       addx -30
                       addx 12
                       addx -1
                       addx 3
                       addx 1
                       noop
                       noop
                       noop
                       addx -9
                       addx 18
                       addx 1
                       addx 2
                       noop
                       noop
                       addx 9
                       noop
                       noop
                       noop
                       addx -1
                       addx 2
                       addx -37
                       addx 1
                       addx 3
                       noop
                       addx 15
                       addx -21
                       addx 22
                       addx -6
                       addx 1
                       noop
                       addx 2
                       addx 1
                       noop
                       addx -10
                       noop
                       noop
                       addx 20
                       addx 1
                       addx 2
                       addx 2
                       addx -6
                       addx -11
                       noop
                       noop
                       noop
                       """;

        assertEquals(13140, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(14520, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       addx 15
                       addx -11
                       addx 6
                       addx -3
                       addx 5
                       addx -1
                       addx -8
                       addx 13
                       addx 4
                       noop
                       addx -1
                       addx 5
                       addx -1
                       addx 5
                       addx -1
                       addx 5
                       addx -1
                       addx 5
                       addx -1
                       addx -35
                       addx 1
                       addx 24
                       addx -19
                       addx 1
                       addx 16
                       addx -11
                       noop
                       noop
                       addx 21
                       addx -15
                       noop
                       noop
                       addx -3
                       addx 9
                       addx 1
                       addx -3
                       addx 8
                       addx 1
                       addx 5
                       noop
                       noop
                       noop
                       noop
                       noop
                       addx -36
                       noop
                       addx 1
                       addx 7
                       noop
                       noop
                       noop
                       addx 2
                       addx 6
                       noop
                       noop
                       noop
                       noop
                       noop
                       addx 1
                       noop
                       noop
                       addx 7
                       addx 1
                       noop
                       addx -13
                       addx 13
                       addx 7
                       noop
                       addx 1
                       addx -33
                       noop
                       noop
                       noop
                       addx 2
                       noop
                       noop
                       noop
                       addx 8
                       noop
                       addx -1
                       addx 2
                       addx 1
                       noop
                       addx 17
                       addx -9
                       addx 1
                       addx 1
                       addx -3
                       addx 11
                       noop
                       noop
                       addx 1
                       noop
                       addx 1
                       noop
                       noop
                       addx -13
                       addx -19
                       addx 1
                       addx 3
                       addx 26
                       addx -30
                       addx 12
                       addx -1
                       addx 3
                       addx 1
                       noop
                       noop
                       noop
                       addx -9
                       addx 18
                       addx 1
                       addx 2
                       noop
                       noop
                       addx 9
                       noop
                       noop
                       noop
                       addx -1
                       addx 2
                       addx -37
                       addx 1
                       addx 3
                       noop
                       addx 15
                       addx -21
                       addx 22
                       addx -6
                       addx 1
                       noop
                       addx 2
                       addx 1
                       noop
                       addx -10
                       noop
                       noop
                       addx 20
                       addx 1
                       addx 2
                       addx 2
                       addx -6
                       addx -11
                       noop
                       noop
                       noop
                       """;

        assertEquals("########", testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("PZBGZEJB", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))));
    }
}