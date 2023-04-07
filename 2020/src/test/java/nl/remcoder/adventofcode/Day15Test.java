package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {
    private Day15 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day15();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       0,3,6
                       """;

        assertEquals(436, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case2() {
        String input = """
                       1,3,2
                       """;

        assertEquals(1, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case3() {
        String input = """
                       2,1,3
                       """;

        assertEquals(10, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case4() {
        String input = """
                       1,2,3
                       """;

        assertEquals(27, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case5() {
        String input = """
                       2,3,1
                       """;

        assertEquals(78, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case6() {
        String input = """
                       3,2,1
                       """;

        assertEquals(438, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Case7() {
        String input = """
                       3,1,2
                       """;

        assertEquals(1836, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(257, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       0,3,6
                       """;

        assertEquals(175594, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case2() {
        String input = """
                       1,3,2
                       """;

        assertEquals(2578, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case3() {
        String input = """
                       2,1,3
                       """;

        assertEquals(3544142, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case4() {
        String input = """
                       1,2,3
                       """;

        assertEquals(261214, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case5() {
        String input = """
                       2,3,1
                       """;

        assertEquals(6895259, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case6() {
        String input = """
                       3,2,1
                       """;

        assertEquals(18, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Case7() {
        String input = """
                       3,1,2
                       """;

        assertEquals(362, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(8546398, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }
}