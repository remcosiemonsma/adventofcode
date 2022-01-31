package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {
    private Day25 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day25();
    }

    @Test
    void testPart1Case1() {
        String data = """
                      v...>>.vv>
                      .vv>>.vv..
                      >>.>v>...v
                      >>v>>.>.v.
                      v>v.vv.v..
                      >.>>..v...
                      .vv..>.>v.
                      v.v..>>v.v
                      ....v..v.>
                      """;

        assertEquals(58, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(560, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day25/input").toURI()))));
    }
}