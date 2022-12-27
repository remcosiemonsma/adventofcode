package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {
    private Day16 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day16();
    }

    @Test
    void part1Case1() {
        String data = """
                      s1,x3/4,pe/b
                      """;

        testSubject.setPrograms("abcde");
        
        assertEquals("baedc", testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setPrograms("abcdefghijklmnop");
        
        assertEquals("giadhmkpcnbfjelo", testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setPrograms("abcdefghijklmnop");
        
        assertEquals("njfgilbkcoemhpad", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()))));
    }
}