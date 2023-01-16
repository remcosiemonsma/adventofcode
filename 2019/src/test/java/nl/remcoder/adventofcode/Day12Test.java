package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    private Day12 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day12();
    }

    @Test
    void testPart1Case1() {
        var input = """
                    <x=-1, y=0, z=2>
                    <x=2, y=-10, z=-7>
                    <x=4, y=-8, z=8>
                    <x=3, y=5, z=-1>
                    """;

        testSubject.setSteps(10);

        assertEquals(179, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        var input = """
                    <x=-8, y=-10, z=0>
                    <x=5, y=5, z=10>
                    <x=2, y=-7, z=3>
                    <x=9, y=-8, z=-3>
                    """;

        testSubject.setSteps(100);

        assertEquals(1940, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        testSubject.setSteps(1000);

        assertEquals(10198, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        var input = """
                    <x=-1, y=0, z=2>
                    <x=2, y=-10, z=-7>
                    <x=4, y=-8, z=8>
                    <x=3, y=5, z=-1>
                    """;

        assertEquals(2772, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case2() {
        var input = """
                    <x=-8, y=-10, z=0>
                    <x=5, y=5, z=10>
                    <x=2, y=-7, z=3>
                    <x=9, y=-8, z=-3>
                    """;

        assertEquals(4686774924L, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(271442326847376L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI()))));
    }
}