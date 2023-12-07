package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {
    private Day7 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day7();
    }

    @Test
    void part1Case1() {
        String data = """
                      32T3K 765
                      T55J5 684
                      KK677 28
                      KTJJT 220
                      QQQJA 483
                      """;

        assertEquals(6440, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(249748283, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      32T3K 765
                      T55J5 684
                      KK677 28
                      KTJJT 220
                      QQQJA 483
                      """;

        assertEquals(5905, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(248029057, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }
}