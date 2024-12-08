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
    void testPart1Case1() {
        String input = """
                       root: pppw + sjmn
                       dbpl: 5
                       cczh: sllz + lgvd
                       zczc: 2
                       ptdq: humn - dvpt
                       dvpt: 3
                       lfqf: 4
                       humn: 5
                       ljgn: 2
                       sjmn: drzm * dbpl
                       sllz: 4
                       pppw: cczh / lfqf
                       lgvd: ljgn * ptdq
                       drzm: hmdt - zczc
                       hmdt: 32
                       """;

        assertEquals(152, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(157714751182692L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2022/day21/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       root: pppw + sjmn
                       dbpl: 5
                       cczh: sllz + lgvd
                       zczc: 2
                       ptdq: humn - dvpt
                       dvpt: 3
                       lfqf: 4
                       humn: 5
                       ljgn: 2
                       sjmn: drzm * dbpl
                       sllz: 4
                       pppw: cczh / lfqf
                       lgvd: ljgn * ptdq
                       drzm: hmdt - zczc
                       hmdt: 32
                       """;

        assertEquals(301, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(3373767893067L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2022/day21/input"))));
    }
}