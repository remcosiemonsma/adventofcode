package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Day8Test {
    private Day8 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day8();
    }

    @Test
    void testPart1Case1() {
        Stream<String> input = Stream.of("123456789012");

        assertEquals(1, testSubject.handlePart1(input, 3, 2, 0, 1, 2));
    }

    @Test
    void testPart1Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()));

        assertEquals(1064, testSubject.handlePart1(input, 25, 6, 0, 1, 2));
    }

    @Test
    void testPart2Case2() {
        Stream<String> input = Stream.of("0222112222120000");

        assertEquals("01\n10\n", testSubject.handlePart2(input, 2, 2, 0, 1, 2));
    }

    @Test
    void testPart2Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()));
        String expectedResult = """
                                1110011110011000110010010
                                1001010000100101001010100
                                1001011100100001001011000
                                1110010000100001111010100
                                1000010000100101001010100
                                1000010000011001001010010
                                """;
        assertEquals(expectedResult, testSubject.handlePart2(input, 25, 6, 0, 1, 2));
    }
}
