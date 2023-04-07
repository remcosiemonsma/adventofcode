package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {
    private Day12 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day12();
    }

    @Test
    public void part1Case1() {
        String data = "[1,2,3]";

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case2() {
        String data = "{\"a\":2,\"b\":4}";

        assertEquals(6, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case3() {
        String data = "[[[3]]]";

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case4() {
        String data = "{\"a\":{\"b\":4},\"c\":-1}";

        assertEquals(3, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case5() {
        String data = "{\"a\":[-1,1]}";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case6() {
        String data = "[-1,{\"a\":1}]";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case7() {
        String data = "[]";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void part1Case8() {
        String data = "{}";

        assertEquals(0, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(119433, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = "[1,2,3]";

        assertEquals(6, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case2() {
        String data = "[1,{\"c\":\"red\",\"b\":2},3]";

        assertEquals(4, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case3() {
        String data = "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}";

        assertEquals(0, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void part2Case4() {
        String data = "[1,\"red\",5]";

        assertEquals(6, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(68466, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))));
    }
}