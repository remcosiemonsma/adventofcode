package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {
    private Day4 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day4();
    }

    @Test
    void testPart1Case1() {
        String data = """
                      7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
                      
                      22 13 17 11  0
                       8  2 23  4 24
                      21  9 14 16  7
                       6 10  3 18  5
                       1 12 20 15 19
                      
                       3 15  0  2 22
                       9 18 13 17  5
                      19  8  7 25 23
                      20 11 10 24  4
                      14 21 16 12  6
                      
                      14 21 17 24  4
                      10 16 15  9 19
                      18  8 23 26 20
                      22 11 13  6  5
                       2  0 12  3  7
                      """;

        assertEquals(4512, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(22680, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String data = """
                      7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
                      
                      22 13 17 11  0
                       8  2 23  4 24
                      21  9 14 16  7
                       6 10  3 18  5
                       1 12 20 15 19
                      
                       3 15  0  2 22
                       9 18 13 17  5
                      19  8  7 25 23
                      20 11 10 24  4
                      14 21 16 12  6
                      
                      14 21 17 24  4
                      10 16 15  9 19
                      18  8 23 26 20
                      22 11 13  6  5
                       2  0 12  3  7
                      """;

        assertEquals(1924, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(16168, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()))));
    }
}