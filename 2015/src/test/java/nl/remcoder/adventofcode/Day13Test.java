package nl.remcoder.adventofcode;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Day13Test {
    private Day13 testSubject;

    @Before
    public void setUp() {
        testSubject = new Day13();
    }

    @Test
    public void part1Case1() {
        String data = "Alice would gain 54 happiness units by sitting next to Bob.\n" +
                      "Alice would lose 79 happiness units by sitting next to Carol.\n" +
                      "Alice would lose 2 happiness units by sitting next to David.\n" +
                      "Bob would gain 83 happiness units by sitting next to Alice.\n" +
                      "Bob would lose 7 happiness units by sitting next to Carol.\n" +
                      "Bob would lose 63 happiness units by sitting next to David.\n" +
                      "Carol would lose 62 happiness units by sitting next to Alice.\n" +
                      "Carol would gain 60 happiness units by sitting next to Bob.\n" +
                      "Carol would gain 55 happiness units by sitting next to David.\n" +
                      "David would gain 46 happiness units by sitting next to Alice.\n" +
                      "David would lose 7 happiness units by sitting next to Bob.\n" +
                      "David would gain 41 happiness units by sitting next to Carol.";

        assertEquals(330, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(664, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day13/input").toURI()))));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(640, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day13/input").toURI()))));
    }
}