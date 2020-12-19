package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test {
    private Day18 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day18();
    }

    @Test
    public void testPart1Case1() {
        String data = """
                1 + 2 * 3 + 4 * 5 + 6
                """;

        Stream<String> input = data.lines();

        assertEquals(71, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case2() {
        String data = """
                1 + (2 * 3) + (4 * (5 + 6))
                """;

        Stream<String> input = data.lines();

        assertEquals(51, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case3() {
        String data = """
                2 * 3 + (4 * 5)
                """;

        Stream<String> input = data.lines();

        assertEquals(26, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case4() {
        String data = """
                5 + (8 * 3 + 9 + 3 * 4 * 3)
                """;

        Stream<String> input = data.lines();

        assertEquals(437, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case5() {
        String data = """
                5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
                """;

        Stream<String> input = data.lines();

        assertEquals(12240, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Case6() {
        String data = """
                ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
                """;

        Stream<String> input = data.lines();

        assertEquals(13632, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(209335026987L, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String data = """
                1 + 2 * 3 + 4 * 5 + 6
                """;

        Stream<String> input = data.lines();

        assertEquals(231, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case2() {
        String data = """
                1 + (2 * 3) + (4 * (5 + 6))
                """;

        Stream<String> input = data.lines();

        assertEquals(51, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case3() {
        String data = """
                2 * 3 + (4 * 5)
                """;

        Stream<String> input = data.lines();

        assertEquals(46, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case4() {
        String data = """
                5 + (8 * 3 + 9 + 3 * 4 * 3)
                """;

        Stream<String> input = data.lines();

        assertEquals(1445, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case5() {
        String data = """
                5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
                """;

        Stream<String> input = data.lines();

        assertEquals(669060, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case6() {
        String data = """
                ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
                """;

        Stream<String> input = data.lines();

        assertEquals(23340, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(33331817392479L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()))));
    }
}