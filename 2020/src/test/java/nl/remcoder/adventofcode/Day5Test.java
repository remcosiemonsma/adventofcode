package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
    private Day5 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day5();
    }

    @Test
    public void testPart1Case1() {
        String data = """
                BFFFBBFRRR
                FFFBBBFRRR
                BBFFBBFRLL
                """;

        assertEquals(820, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(816, testSubject.handlePart1(Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(539, testSubject.handlePart2(Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }
}