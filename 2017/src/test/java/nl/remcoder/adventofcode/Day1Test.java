package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {
    private Day1 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day1();
    }

    @Test
    void part1Case1() {
        String data = "1122";

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = "1111";

        assertEquals(4, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case3() {
        String data = "1234";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case4() {
        String data = "91212129";

        assertEquals(9, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1049, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = "1212";

        assertEquals(6, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = "1221";

        assertEquals(0, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case3() {
        String data = "123425";

        assertEquals(4, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case4() {
        String data = "123123";

        assertEquals(12, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case5() {
        String data = "12131415";

        assertEquals(4, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1508, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))));
    }
}