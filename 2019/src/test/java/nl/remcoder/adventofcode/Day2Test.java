package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {
    private Day2 testSubject;

    @BeforeEach
    public void beforeEach() {
        testSubject = new Day2();
    }

    @Test
    public void testPart1Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()));

        assertEquals(2692315, testSubject.handlePart1(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day2/input").toURI()));

        assertEquals(9507, testSubject.handlePart2(input, 19690720));
    }
}