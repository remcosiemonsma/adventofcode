package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test {
    private Day15 testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new Day15();
    }

    @Test
    public void part1Case1() {
        String data = """
                      Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                      Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
                      """;

        assertEquals(62842880, testSubject.handlePart1(data.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(13882464, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }

    @Test
    public void part2Case1() {
        String data = """
                      Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                      Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
                      """;

        assertEquals(57600000, testSubject.handlePart2(data.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(11171160, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day15/input").toURI()))));
    }
}