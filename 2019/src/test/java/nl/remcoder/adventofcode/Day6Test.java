package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {

    private Day6 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day6();
    }

    @Test
    void testPart1Case1() {
        Stream<String> input = Stream.of("COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L");

        assertEquals(42, testSubject.handlePart1(input));
    }

    @Test
    void testPart1Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()));

        System.out.println(testSubject.handlePart1(input));
    }

    @Test
    void testPart2Case1() {
        Stream<String> input = Stream.of("COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L", "K)YOU", "I)SAN");

        assertEquals(4, testSubject.handlePart2(input));
    }

    @Test
    void testPart2Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()));

        System.out.println(testSubject.handlePart2(input));
    }
}