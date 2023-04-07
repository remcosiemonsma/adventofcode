package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {
    private Day10 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day10();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       [({(<(())[]>[[{[]{<()<>>
                       [(()[<>])]({[<{<<[]>>(
                       {([(<{}[<>[]}>{[]{[(<()>
                       (((({<>}<{<{<>}{[]{[]{}
                       [[<[([]))<([[{}[[()]]]
                       [{[{({}]{}}([{[{{{}}([]
                       {<[[]]>}<{[{[{[]{()[[[]
                       [<(<(<(<{}))><([]([]()
                       <{([([[(<>()){}]>(<<{{
                       <{([{{}}[<[[[<>{}]]]>[]]
                       """;

        assertEquals(26397, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(394647, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       [({(<(())[]>[[{[]{<()<>>
                       [(()[<>])]({[<{<<[]>>(
                       {([(<{}[<>[]}>{[]{[(<()>
                       (((({<>}<{<{<>}{[]{[]{}
                       [[<[([]))<([[{}[[()]]]
                       [{[{({}]{}}([{[{{{}}([]
                       {<[[]]>}<{[{[{[]{()[[[]
                       [<(<(<(<{}))><([]([]()
                       <{([([[(<>()){}]>(<<{{
                       <{([{{}}[<[[[<>{}]]]>[]]
                       """;

        assertEquals(288957, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2380061249L, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))));
    }
}