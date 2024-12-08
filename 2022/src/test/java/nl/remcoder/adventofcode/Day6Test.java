package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {
    private Day6 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day6();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       mjqjpqmgbljsphdztnvjfqwrcgsmlb
                       """;

        assertEquals(7, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        String input = """
                       bvwbjplbgvbhsrlpgdmjqwftvncz
                       """;

        assertEquals(5, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case3() {
        String input = """
                       nppdvjthqldpwncqszvftbrmjlhg
                       """;

        assertEquals(6, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case4() {
        String input = """
                       nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg
                       """;

        assertEquals(10, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case5() {
        String input = """
                       zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw
                       """;

        assertEquals(11, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1100, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day6/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       mjqjpqmgbljsphdztnvjfqwrcgsmlb
                       """;

        assertEquals(19, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case2() {
        String input = """
                       bvwbjplbgvbhsrlpgdmjqwftvncz
                       """;

        assertEquals(23, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case3() {
        String input = """
                       nppdvjthqldpwncqszvftbrmjlhg
                       """;

        assertEquals(23, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case4() {
        String input = """
                       nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg
                       """;

        assertEquals(29, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case5() {
        String input = """
                       zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw
                       """;

        assertEquals(26, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2421, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day6/input"))));
    }
}