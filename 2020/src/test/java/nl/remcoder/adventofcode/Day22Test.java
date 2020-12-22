package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day22Test {
    private Day22 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day22();
    }

    @Test
    public void testPart1Case1() {
        String data = """
                Player 1:
                9
                2
                6
                3
                1
                                
                Player 2:
                5
                8
                4
                7
                10
                """;

        Stream<String> input = data.lines();

        assertEquals(306, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(32472, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String data = """
                Player 1:
                9
                2
                6
                3
                1
                                
                Player 2:
                5
                8
                4
                7
                10
                """;

        Stream<String> input = data.lines();

        assertEquals(291, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(36463, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI()))));
    }
}