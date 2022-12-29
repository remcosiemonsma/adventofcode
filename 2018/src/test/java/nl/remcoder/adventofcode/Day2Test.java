package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {
    private Day2 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day2();
    }

    @Test
    void part1Case1() {
        String data = """
                      abcdef
                      bababc
                      abbcde
                      abcccd
                      aabcdd
                      abcdee
                      ababab
                      """;

        assertEquals(12, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(6723, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      abcde
                      fghij
                      klmno
                      pqrst
                      fguij
                      axcye
                      wvxyz
                      """;

        assertEquals("fgij", testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("prtkqyluiusocwvaezjmhmfgx", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()))));
    }
}