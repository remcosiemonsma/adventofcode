package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {
    private Day11 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day11();
    }

    @Test
    public void testPart1Case1() {
        String data = """
                L.LL.LL.LL
                LLLLLLL.LL
                L.L.L..L..
                LLLL.LL.LL
                L.LL.LL.LL
                L.LLLLL.LL
                ..L.L.....
                LLLLLLLLLL
                L.LLLLLL.L
                L.LLLLL.LL
                """;

        Stream<String> input = data.lines();

        assertEquals(37, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(2316, testSubject.handlePart1(Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String data = """
                L.LL.LL.LL
                LLLLLLL.LL
                L.L.L..L..
                LLLL.LL.LL
                L.LL.LL.LL
                L.LLLLL.LL
                ..L.L.....
                LLLLLLLLLL
                L.LLLLLL.L
                L.LLLLL.LL
                """;

        Stream<String> input = data.lines();

        assertEquals(26, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(2128, testSubject.handlePart2(Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI()))));
    }
}