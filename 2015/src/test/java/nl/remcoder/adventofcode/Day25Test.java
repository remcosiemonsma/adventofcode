package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {
    private Day25 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day25();
    }

    @Test
    void testPart1Case1() {
        String input = "To continue, please consult the code grid in the manual.  Enter the code at row 2, column 1.";

        assertEquals(31916031, testSubject.handlePart1(input.lines()));
    }
    @Test
    void testPart1Case2() {
        String input = "To continue, please consult the code grid in the manual.  Enter the code at row 1, column 2.";

        assertEquals(18749137, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case3() {
        String input = "To continue, please consult the code grid in the manual.  Enter the code at row 3, column 4.";

        assertEquals(7981243, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(19980801, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day25/input").toURI()))));
    }
}