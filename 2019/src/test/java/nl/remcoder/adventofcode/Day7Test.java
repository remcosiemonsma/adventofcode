package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {

    private Day7 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day7();
    }

    @Test
    void testPart1Case1() {
        Stream<String> input = Stream.of("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0");

        assertEquals(43210, testSubject.handlePart1(input));
    }

    @Test
    void testPart1Case2() {
        Stream<String> input = Stream.of("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0");

        assertEquals(54321, testSubject.handlePart1(input));
    }

    @Test
    void testPart1Case3() {
        Stream<String> input = Stream.of("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0");

        assertEquals(65210, testSubject.handlePart1(input));
    }

    @Test
    void testPart1Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()));

        System.out.println(testSubject.handlePart1(input));
    }
}