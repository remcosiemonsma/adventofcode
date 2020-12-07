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
    public void testPart1Case1() {
        String data = """
                light red bags contain 1 bright white bag, 2 muted yellow bags.
                dark orange bags contain 3 bright white bags, 4 muted yellow bags.
                bright white bags contain 1 shiny gold bag.
                muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
                shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
                dark olive bags contain 3 faded blue bags, 4 dotted black bags.
                vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
                faded blue bags contain no other bags.
                dotted black bags contain no other bags.
                """;

        Stream<String> input = data.lines();

        assertEquals(4, testSubject.handlePart1(input));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(252, testSubject.handlePart1(Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String data = """
                light red bags contain 1 bright white bag, 2 muted yellow bags.
                dark orange bags contain 3 bright white bags, 4 muted yellow bags.
                bright white bags contain 1 shiny gold bag.
                muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
                shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
                dark olive bags contain 3 faded blue bags, 4 dotted black bags.
                vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
                faded blue bags contain no other bags.
                dotted black bags contain no other bags.
                """;

        Stream<String> input = data.lines();

        assertEquals(32, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Case2() {
        String data = """
                shiny gold bags contain 2 dark red bags.
                dark red bags contain 2 dark orange bags.
                dark orange bags contain 2 dark yellow bags.
                dark yellow bags contain 2 dark green bags.
                dark green bags contain 2 dark blue bags.
                dark blue bags contain 2 dark violet bags.
                dark violet bags contain no other bags.
                """;

        Stream<String> input = data.lines();

        assertEquals(126, testSubject.handlePart2(input));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(35487, testSubject.handlePart2(Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }
}