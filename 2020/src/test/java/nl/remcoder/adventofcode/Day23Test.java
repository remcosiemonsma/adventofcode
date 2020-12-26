package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day23Test {
    private Day23 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day23();
    }

    @Test
    public void testPart1Case1() {
        String data = "389125467";

        Stream<String> input = data.lines();

        assertEquals(92658374, testSubject.handlePart1(input, 10));
    }

    @Test
    public void testPart1Case2() {
        String data = "389125467";

        Stream<String> input = data.lines();

        assertEquals(67384529, testSubject.handlePart1(input, 100));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(76952348, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI())), 100));
    }

    @Test
    public void testPart2Case1() {
        String data = "389125467";

        Stream<String> input = data.lines();

        assertEquals(149245887792L, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(72772522064L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day23/input").toURI()))));
    }
}