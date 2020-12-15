package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {
    private Day15 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day15();
    }

    @Test
    public void testPart1Case1() {
        String data = """
                0,3,6
                """;

        Stream<String> input = data.lines();

        assertEquals(436, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case2() {
        String data = """
                1,3,2
                """;

        Stream<String> input = data.lines();

        assertEquals(1, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case3() {
        String data = """
                2,1,3
                """;

        Stream<String> input = data.lines();

        assertEquals(10, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case4() {
        String data = """
                1,2,3
                """;

        Stream<String> input = data.lines();

        assertEquals(27, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case5() {
        String data = """
                2,3,1
                """;

        Stream<String> input = data.lines();

        assertEquals(78, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case6() {
        String data = """
                3,2,1
                """;

        Stream<String> input = data.lines();

        assertEquals(438, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case7() {
        String data = """
                3,1,2
                """;

        Stream<String> input = data.lines();

        assertEquals(1836, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(257, testSubject.handlePart1(Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String data = """
                0,3,6
                """;

        Stream<String> input = data.lines();

        assertEquals(175594, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case2() {
        String data = """
                1,3,2
                """;

        Stream<String> input = data.lines();

        assertEquals(2578, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case3() {
        String data = """
                2,1,3
                """;

        Stream<String> input = data.lines();

        assertEquals(3544142, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case4() {
        String data = """
                1,2,3
                """;

        Stream<String> input = data.lines();

        assertEquals(261214, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case5() {
        String data = """
                2,3,1
                """;

        Stream<String> input = data.lines();

        assertEquals(6895259, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case6() {
        String data = """
                3,2,1
                """;

        Stream<String> input = data.lines();

        assertEquals(18, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case7() {
        String data = """
                3,1,2
                """;

        Stream<String> input = data.lines();

        assertEquals(362, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(8546398, testSubject.handlePart2(Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }
}