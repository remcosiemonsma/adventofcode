package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class Day8Test {
    private Day8 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day8();
    }

    @Test
    void testPart1Case1() {
        var input = "123456789012";

        testSubject.setWidth(3);
        testSubject.setHeight(2);

        assertEquals(1, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setWidth(25);
        testSubject.setHeight(6);

        assertEquals(1064, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setWidth(25);
        testSubject.setHeight(6);

        assertEquals("PFCAK", testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI()))));
    }
}
