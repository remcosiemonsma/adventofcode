package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
    private Day5 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day5();
    }

    @Test
    void part1Case1() {
        String data = """
                      47|53
                      97|13
                      97|61
                      97|47
                      75|29
                      61|13
                      75|53
                      29|13
                      97|29
                      53|29
                      61|53
                      97|53
                      61|29
                      47|13
                      75|47
                      97|75
                      47|61
                      75|61
                      47|29
                      75|13
                      53|13
                
                      75,47,61,53,29
                      97,61,53,29,13
                      75,29,13
                      75,97,47,61,53
                      61,13,29
                      97,13,75,29,47
                      """;

        assertEquals(143, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(4662, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      47|53
                      97|13
                      97|61
                      97|47
                      75|29
                      61|13
                      75|53
                      29|13
                      97|29
                      53|29
                      61|53
                      97|53
                      61|29
                      47|13
                      75|47
                      97|75
                      47|61
                      75|61
                      47|29
                      75|13
                      53|13
                
                      75,47,61,53,29
                      97,61,53,29,13
                      75,29,13
                      75,97,47,61,53
                      61,13,29
                      97,13,75,29,47
                      """;

        assertEquals(123, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(5900, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()))));
    }
}