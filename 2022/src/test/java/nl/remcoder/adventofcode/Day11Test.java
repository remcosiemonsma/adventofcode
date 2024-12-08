package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {
    private Day11 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day11();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       Monkey 0:
                         Starting items: 79, 98
                         Operation: new = old * 19
                         Test: divisible by 23
                           If true: throw to monkey 2
                           If false: throw to monkey 3
                                              
                       Monkey 1:
                         Starting items: 54, 65, 75, 74
                         Operation: new = old + 6
                         Test: divisible by 19
                           If true: throw to monkey 2
                           If false: throw to monkey 0
                                              
                       Monkey 2:
                         Starting items: 79, 60, 97
                         Operation: new = old * old
                         Test: divisible by 13
                           If true: throw to monkey 1
                           If false: throw to monkey 3
                                              
                       Monkey 3:
                         Starting items: 74
                         Operation: new = old + 3
                         Test: divisible by 17
                           If true: throw to monkey 0
                           If false: throw to monkey 1
                       """;

        assertEquals(10605, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(90882, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day11/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       Monkey 0:
                         Starting items: 79, 98
                         Operation: new = old * 19
                         Test: divisible by 23
                           If true: throw to monkey 2
                           If false: throw to monkey 3
                                              
                       Monkey 1:
                         Starting items: 54, 65, 75, 74
                         Operation: new = old + 6
                         Test: divisible by 19
                           If true: throw to monkey 2
                           If false: throw to monkey 0
                                              
                       Monkey 2:
                         Starting items: 79, 60, 97
                         Operation: new = old * old
                         Test: divisible by 13
                           If true: throw to monkey 1
                           If false: throw to monkey 3
                                              
                       Monkey 3:
                         Starting items: 74
                         Operation: new = old + 3
                         Test: divisible by 17
                           If true: throw to monkey 0
                           If false: throw to monkey 1
                       """;

        assertEquals(2713310158L, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(30893109657L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day11/input"))));
    }
}