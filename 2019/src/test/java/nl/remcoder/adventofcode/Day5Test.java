package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
    private Day5 testSubject;

    @BeforeEach
    public void beforeEach() {
        testSubject = new Day5();
    }

    @Test
    public void testPart1Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()));

        System.out.println(testSubject.handlePart1(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()));

        System.out.println(testSubject.handlePart2(input));
    }
}