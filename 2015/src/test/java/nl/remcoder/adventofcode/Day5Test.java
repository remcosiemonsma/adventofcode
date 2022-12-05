package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {
    private Day5 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day5();
    }

    @Test
    public void part1Case1() {
        String data = "ugknbfddgicrmopn";

        assertEquals(1, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case2() {
        String data = "aaa";

        assertEquals(1, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case3() {
        String data = "jchzalrnumimnmhp";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case4() {
        String data = "haegwjzuvuyypxyu";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case5() {
        String data = "dvszwmarrgswjxmb";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(255, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "qjhvhtzxzqqjkmpb";

        assertEquals(1, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case2() {
        String data = "xxyxx";

        assertEquals(1, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case3() {
        String data = "uurcxstgmygtbstg";

        assertEquals(0, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case4() {
        String data = "ieodomkazucvgmuy";

        assertEquals(0, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(55, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }
}