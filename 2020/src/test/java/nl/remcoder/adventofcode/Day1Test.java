package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    private Day1 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day1();
    }

    @Test
    public void part1Case1() {
        Stream<String> input = Stream.of("1721", "979", "366", "299", "675", "1456");

        assertEquals(514579, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(980499, testSubject.handlePart1(Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        Stream<String> input = Stream.of("1721", "979", "366", "299", "675", "1456");

        assertEquals(241861950, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(200637446, testSubject.handlePart2(Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))));
    }
}