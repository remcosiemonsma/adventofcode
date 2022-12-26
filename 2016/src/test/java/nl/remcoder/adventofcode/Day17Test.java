package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {
    private Day17 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day17();
    }

    @Test
    void part1Case1() {
        String data = """
                      ihgpwlah
                      """;
        
        assertEquals("DDRRRD", testSubject.handlePart1(data.lines()));
    }
    
    @Test
    void part1Case2() {
        String data = """
                      kglvqrro
                      """;
        
        assertEquals("DDUDRLRRUDRD", testSubject.handlePart1(data.lines()));
    }
    
    @Test
    void part1Case3() {
        String data = """
                      ulqzkmiv
                      """;
        
        assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals("DUDDRLRRRD", testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      ihgpwlah
                      """;

        assertEquals(370, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      kglvqrro
                      """;

        assertEquals(492, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case3() {
        String data = """
                      ulqzkmiv
                      """;

        assertEquals(830, testSubject.handlePart2(data.lines()));
    }
    
    @Test
    void testPart2Input() throws Exception {
        assertEquals(578, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()))));
    }
}