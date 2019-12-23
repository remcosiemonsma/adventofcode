package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    private Day12 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day12();
    }

    @Test
    void testPart1Case1() {
        Stream<String> input = Stream.of("<x=-1, y=0, z=2>", "<x=2, y=-10, z=-7>", "<x=4, y=-8, z=8>", "<x=3, y=5, z=-1>");

        assertEquals(179, testSubject.handlePart1(input, 10));
    }

    @Test
    void testPart1Case2() {
        Stream<String> input = Stream.of("<x=-8, y=-10, z=0>", "<x=5, y=5, z=10>", "<x=2, y=-7, z=3>", "<x=9, y=-8, z=-3>");

        assertEquals(1940, testSubject.handlePart1(input, 100));
    }

    @Test
    void testPart1Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()));

        assertEquals(10198, testSubject.handlePart1(input, 1000));
    }

    @Test
    void testPart2Case1() {
        Stream<String> input = Stream.of("<x=-1, y=0, z=2>", "<x=2, y=-10, z=-7>", "<x=4, y=-8, z=8>", "<x=3, y=5, z=-1>");

        assertEquals(2772, testSubject.handlePart2(input));
    }

    @Test
    void testPart2Case2() {
        Stream<String> input = Stream.of("<x=-8, y=-10, z=0>", "<x=5, y=5, z=10>", "<x=2, y=-7, z=3>", "<x=9, y=-8, z=-3>");

        assertEquals(4686774924L, testSubject.handlePart2(input));
    }

    @Test
    void testPart2Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()));

        assertEquals(271442326847376L, testSubject.handlePart2(input));
    }
}