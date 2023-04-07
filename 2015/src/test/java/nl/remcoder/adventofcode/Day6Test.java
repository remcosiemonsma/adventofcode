package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {
    private Day6 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day6();
    }

    @Test
    public void part1Case1() {
        String data = "turn on 0,0 through 999,999";

        assertEquals(1_000_000, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case2() {
        String data = "toggle 0,0 through 999,0";

        assertEquals(1_000, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case3() {
        String data = "turn on 0,0 through 999,999\ntoggle 0,0 through 999,0\nturn off 499,499 through 500,500";

        assertEquals(998_996, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(543903, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "turn on 0,0 through 0,0";

        assertEquals(1, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case2() {
        String data = "toggle 0,0 through 999,999";

        assertEquals(2_000_000, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(14687245, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()))));
    }
}