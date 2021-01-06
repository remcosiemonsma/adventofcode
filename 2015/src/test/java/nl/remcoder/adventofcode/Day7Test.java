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
        String data = "123 -> x\n" +
                      "456 -> y\n" +
                      "x AND y -> d\n" +
                      "x OR y -> e\n" +
                      "x LSHIFT 2 -> f\n" +
                      "y RSHIFT 2 -> g\n" +
                      "NOT x -> h\n" +
                      "NOT y -> i\n" +
                      "d -> a";

        assertEquals(72, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case2() {
        String data = "123 -> x\n" +
                      "456 -> y\n" +
                      "x AND y -> d\n" +
                      "x OR y -> e\n" +
                      "x LSHIFT 2 -> f\n" +
                      "y RSHIFT 2 -> g\n" +
                      "NOT x -> h\n" +
                      "NOT y -> i\n" +
                      "e -> a";

        assertEquals(507, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case3() {
        String data = "123 -> x\n" +
                      "456 -> y\n" +
                      "x AND y -> d\n" +
                      "x OR y -> e\n" +
                      "x LSHIFT 2 -> f\n" +
                      "y RSHIFT 2 -> g\n" +
                      "NOT x -> h\n" +
                      "NOT y -> i\n" +
                      "f -> a";

        assertEquals(492, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case4() {
        String data = "123 -> x\n" +
                      "456 -> y\n" +
                      "x AND y -> d\n" +
                      "x OR y -> e\n" +
                      "x LSHIFT 2 -> f\n" +
                      "y RSHIFT 2 -> g\n" +
                      "NOT x -> h\n" +
                      "NOT y -> i\n" +
                      "g -> a";

        assertEquals(114, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case5() {
        String data = "123 -> x\n" +
                      "456 -> y\n" +
                      "x AND y -> d\n" +
                      "x OR y -> e\n" +
                      "x LSHIFT 2 -> f\n" +
                      "y RSHIFT 2 -> g\n" +
                      "NOT x -> h\n" +
                      "NOT y -> i\n" +
                      "h -> a";

        assertEquals(65412, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case6() {
        String data = "123 -> x\n" +
                      "456 -> y\n" +
                      "x AND y -> d\n" +
                      "x OR y -> e\n" +
                      "x LSHIFT 2 -> f\n" +
                      "y RSHIFT 2 -> g\n" +
                      "NOT x -> h\n" +
                      "NOT y -> i\n" +
                      "i -> a";

        assertEquals(65079, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case7() {
        String data = "123 -> x\n" +
                      "456 -> y\n" +
                      "x AND y -> d\n" +
                      "x OR y -> e\n" +
                      "x LSHIFT 2 -> f\n" +
                      "y RSHIFT 2 -> g\n" +
                      "NOT x -> h\n" +
                      "NOT y -> i\n" +
                      "x -> a";

        assertEquals(123, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void part1Case8() {
        String data = "123 -> x\n" +
                      "456 -> y\n" +
                      "x AND y -> d\n" +
                      "x OR y -> e\n" +
                      "x LSHIFT 2 -> f\n" +
                      "y RSHIFT 2 -> g\n" +
                      "NOT x -> h\n" +
                      "NOT y -> i\n" +
                      "y -> a";

        assertEquals(456, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(46065, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(14134, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }
}