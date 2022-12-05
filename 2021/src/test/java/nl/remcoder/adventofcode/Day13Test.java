package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {
    private Day13 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day13();
    }

    @Test
    void testPart1Case1() {
        String data = """
                      6,10
                      0,14
                      9,10
                      0,3
                      10,4
                      4,11
                      6,0
                      6,12
                      4,1
                      0,13
                      10,12
                      3,4
                      3,0
                      8,4
                      1,10
                      2,14
                      8,10
                      9,0
                                            
                      fold along y=7
                      fold along x=5
                      """;

        assertEquals(17, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(731, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day13/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("ZKAUCFUC", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day13/input").toURI()))));
    }
}