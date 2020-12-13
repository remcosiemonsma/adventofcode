package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {
    private Day13 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day13();
    }

    @Test
    public void testPart1Case1() {
        String data = """
                939
                7,13,x,x,59,x,31,19
                """;

        Stream<String> input = data.lines();

        assertEquals(295, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(4315, testSubject.handlePart1(Files.lines(Paths.get(ClassLoader.getSystemResource("day13/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String data = """
                939
                7,13,x,x,59,x,31,19
                """;

        Stream<String> input = data.lines();

        assertEquals(1068781, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case2() {
        String data = """
                939
                17,x,13,19
                """;

        Stream<String> input = data.lines();

        assertEquals(3417, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case3() {
        String data = """
                939
                67,7,59,61
                """;

        Stream<String> input = data.lines();

        assertEquals(754018, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case4() {
        String data = """
                939
                67,x,7,59,61
                """;

        Stream<String> input = data.lines();

        assertEquals(779210, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case5() {
        String data = """
                939
                67,7,x,59,61
                """;

        Stream<String> input = data.lines();

        assertEquals(1261476, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case6() {
        String data = """
                939
                1789,37,47,1889
                """;

        Stream<String> input = data.lines();

        assertEquals(1202161486, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(556100168221141L, testSubject.handlePart2(Files.lines(Paths.get(ClassLoader.getSystemResource("day13/input").toURI()))));
    }
}