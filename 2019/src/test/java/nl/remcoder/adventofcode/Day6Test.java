package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {

    private Day6 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day6();
    }

    @Test
    void testPart1Case1() {
        var input = """
                    COM)B
                    B)C
                    C)D
                    D)E
                    E)F
                    B)G
                    G)H
                    D)I
                    E)J
                    J)K
                    K)L
                    """;

        assertEquals(42, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(295936, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        var input = """
                    COM)B
                    B)C
                    C)D
                    D)E
                    E)F
                    B)G
                    G)H
                    D)I
                    E)J
                    J)K
                    K)L
                    K)YOU
                    I)SAN
                    """;

        assertEquals(4, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(457, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()))));
    }
}