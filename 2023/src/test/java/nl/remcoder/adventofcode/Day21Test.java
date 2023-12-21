package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day21Test {
    private Day21 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day21();
    }

    @Test
    void part1Case1() {
        String data = """
                      ...........
                      .....###.#.
                      .###.##..#.
                      ..#.#...#..
                      ....#.#....
                      .##..S####.
                      .##..#...#.
                      .......##..
                      .##.#.####.
                      .##..##.##.
                      ...........
                      """;

        testSubject.setSteps(6);

        assertEquals(16, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(3632, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day21/input").toURI()))));
    }

    @Test
    void part2Case1() {
        String data = """
                      ...........
                      .....###.#.
                      .###.##..#.
                      ..#.#...#..
                      ....#.#....
                      .##..S####.
                      .##..#...#.
                      .......##..
                      .##.#.####.
                      .##..##.##.
                      ...........
                      """;

        testSubject.setSteps(6);

        assertEquals(16, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case2() {
        String data = """
                      ...........
                      .....###.#.
                      .###.##..#.
                      ..#.#...#..
                      ....#.#....
                      .##..S####.
                      .##..#...#.
                      .......##..
                      .##.#.####.
                      .##..##.##.
                      ...........
                      """;

        testSubject.setSteps(10);

        assertEquals(50, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case3() {
        String data = """
                      ...........
                      .....###.#.
                      .###.##..#.
                      ..#.#...#..
                      ....#.#....
                      .##..S####.
                      .##..#...#.
                      .......##..
                      .##.#.####.
                      .##..##.##.
                      ...........
                      """;

        testSubject.setSteps(50);

        assertEquals(1594, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case4() {
        String data = """
                      ...........
                      .....###.#.
                      .###.##..#.
                      ..#.#...#..
                      ....#.#....
                      .##..S####.
                      .##..#...#.
                      .......##..
                      .##.#.####.
                      .##..##.##.
                      ...........
                      """;

        testSubject.setSteps(100);

        assertEquals(6536, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case5() {
        String data = """
                      ...........
                      .....###.#.
                      .###.##..#.
                      ..#.#...#..
                      ....#.#....
                      .##..S####.
                      .##..#...#.
                      .......##..
                      .##.#.####.
                      .##..##.##.
                      ...........
                      """;

        testSubject.setSteps(500);

        assertEquals(167004, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case6() {
        String data = """
                      ...........
                      .....###.#.
                      .###.##..#.
                      ..#.#...#..
                      ....#.#....
                      .##..S####.
                      .##..#...#.
                      .......##..
                      .##.#.####.
                      .##..##.##.
                      ...........
                      """;

        testSubject.setSteps(1000);

        assertEquals(668697, testSubject.handlePart2(data.lines()));
    }

    @Test
    void part2Case7() {
        String data = """
                      ...........
                      .....###.#.
                      .###.##..#.
                      ..#.#...#..
                      ....#.#....
                      .##..S####.
                      .##..#...#.
                      .......##..
                      .##.#.####.
                      .##..##.##.
                      ...........
                      """;

        testSubject.setSteps(5000);

        assertEquals(16733044, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        testSubject.setSteps(26501365);

        assertEquals(600336060511101L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day21/input").toURI()))));
    }
}