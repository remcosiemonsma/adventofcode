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
    public void testPart1Case1() {
        String input = """
                       16
                       10
                       15
                       5
                       1
                       11
                       7
                       19
                       6
                       12
                       4
                       """;

        assertEquals(35, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case2() {
        String input = """
                       28
                       33
                       18
                       42
                       31
                       14
                       46
                       20
                       48
                       47
                       24
                       23
                       49
                       45
                       19
                       38
                       39
                       11
                       1
                       32
                       25
                       35
                       8
                       17
                       7
                       9
                       4
                       2
                       34
                       10
                       3
                       """;

        assertEquals(220, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(2240, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       16
                       10
                       15
                       5
                       1
                       11
                       7
                       19
                       6
                       12
                       4
                       """;

        assertEquals(8, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case2() {
        String input = """
                       28
                       33
                       18
                       42
                       31
                       14
                       46
                       20
                       48
                       47
                       24
                       23
                       49
                       45
                       19
                       38
                       39
                       11
                       1
                       32
                       25
                       35
                       8
                       17
                       7
                       9
                       4
                       2
                       34
                       10
                       3
                       """;

        assertEquals(19208, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(99214346656768L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))));
    }
}