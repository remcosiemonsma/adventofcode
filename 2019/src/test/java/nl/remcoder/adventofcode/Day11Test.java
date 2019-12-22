package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {
    private Day11 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day11();
    }

    @Test
    void testPart1Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI()));

        assertEquals(1747, testSubject.handlePart1(input));
    }

    @Test
    void testPart2Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI()));

        assertEquals(249, testSubject.handlePart2(input));
    }
}