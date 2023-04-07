package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    private Day14 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day14();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       NNCB
                                             
                       CH -> B
                       HH -> N
                       CB -> H
                       NH -> C
                       HB -> C
                       HC -> B
                       HN -> C
                       NN -> C
                       BH -> H
                       NC -> B
                       NB -> B
                       BN -> B
                       BB -> N
                       BC -> B
                       CC -> N
                       CN -> C
                       """;

        assertEquals(1588, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(2345, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day14/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       NNCB
                                             
                       CH -> B
                       HH -> N
                       CB -> H
                       NH -> C
                       HB -> C
                       HC -> B
                       HN -> C
                       NN -> C
                       BH -> H
                       NC -> B
                       NB -> B
                       BN -> B
                       BB -> N
                       BC -> B
                       CC -> N
                       CN -> C
                       """;

        assertEquals(2188189693529L, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2432786807053L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day14/input").toURI()))));
    }
}