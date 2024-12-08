package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {
    private Day16 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day16();
    }

    @Test
    void part1Case1() {
        String data = """
                      .|...\\....
                      |.-.\\.....
                      .....|-...
                      ........|.
                      ..........
                      .........\\
                      ..../.\\\\..
                      .-.-/..|..
                      .|....-|.\\
                      ..//.|....
                      """;

        assertEquals(46, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(7496, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day16/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      .|...\\....
                      |.-.\\.....
                      .....|-...
                      ........|.
                      ..........
                      .........\\
                      ..../.\\\\..
                      .-.-/..|..
                      .|....-|.\\
                      ..//.|....
                      """;

        assertEquals(51, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(7932, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day16/input"))));
    }
}