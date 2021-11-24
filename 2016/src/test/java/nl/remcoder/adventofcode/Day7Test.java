package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Day7Test {
    private Day7 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day7();
    }

    @Test
    public void part1Case1() {
        String data = "abba[mnop]qrst";

        assertEquals(1, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case2() {
        String data = "abcd[bddb]xyyx";

        assertEquals(0, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case3() {
        String data = "aaaa[qwer]tyui";

        assertEquals(0, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case4() {
        String data = "ioxxoj[asdfgh]zxcvbn";

        assertEquals(1, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(115, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "aba[bab]xyz";

        assertEquals(1, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part2Case2() {
        String data = "xyx[xyx]xyx";

        assertEquals(0, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part2Case3() {
        String data = "aaa[kek]eke";

        assertEquals(1, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part2Case4() {
        String data = "zazbz[bzb]cdb";

        assertEquals(1, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(231, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }
}