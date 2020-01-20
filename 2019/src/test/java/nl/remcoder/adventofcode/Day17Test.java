package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {
    private Day17 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day17();
    }

    @Test
    void testPart1Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()));

        assertEquals(4800, testSubject.handlePart1(input));
    }
}