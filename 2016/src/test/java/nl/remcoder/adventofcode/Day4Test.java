package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day4Test {
    private Day4 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day4();
    }

    @Test
    public void part1Case1() {
        String data = "aaaaa-bbb-z-y-x-123[abxyz]\n" +
                      "a-b-c-d-e-f-g-h-987[abcde]\n" +
                      "not-a-real-room-404[oarel]\n" +
                      "totally-real-room-200[decoy]";

        assertEquals(1514, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(185371, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "qzmt-zixmtkozy-ivhz-343[zimth]";

        assertEquals(0, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(984, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()))));
    }
}