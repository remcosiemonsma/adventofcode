package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {
    private Day4 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day4();
    }

    @Test
    void part1Case1() {
        String data = """
                      aaaaa-bbb-z-y-x-123[abxyz]
                      a-b-c-d-e-f-g-h-987[abcde]
                      not-a-real-room-404[oarel]
                      totally-real-room-200[decoy]
                      """;

        assertEquals(1514, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(185371, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = "qzmt-zixmtkozy-ivhz-343[zimth]";

        assertEquals(0, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(984, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()))));
    }
}