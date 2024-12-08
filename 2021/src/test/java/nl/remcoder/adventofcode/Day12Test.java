package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    private Day12 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day12();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       start-A
                       start-b
                       A-c
                       A-b
                       b-d
                       A-end
                       b-end
                       """;

        assertEquals(10, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        String input = """
                       dc-end
                       HN-start
                       start-kj
                       dc-start
                       dc-HN
                       LN-dc
                       HN-end
                       kj-sa
                       kj-HN
                       kj-dc
                       """;

        assertEquals(19, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case3() {
        String input = """
                       fs-end
                       he-DX
                       fs-he
                       start-DX
                       pj-DX
                       end-zg
                       zg-sl
                       zg-pj
                       pj-he
                       RW-he
                       fs-DX
                       pj-RW
                       zg-RW
                       start-pj
                       he-WI
                       zg-he
                       pj-fs
                       start-RW
                       """;

        assertEquals(226, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(3463, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2021/day12/input"))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       start-A
                       start-b
                       A-c
                       A-b
                       b-d
                       A-end
                       b-end
                       """;

        assertEquals(36, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case2() {
        String input = """
                       dc-end
                       HN-start
                       start-kj
                       dc-start
                       dc-HN
                       LN-dc
                       HN-end
                       kj-sa
                       kj-HN
                       kj-dc
                       """;

        assertEquals(103, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case3() {
        String input = """
                       fs-end
                       he-DX
                       fs-he
                       start-DX
                       pj-DX
                       end-zg
                       zg-sl
                       zg-pj
                       pj-he
                       RW-he
                       fs-DX
                       pj-RW
                       zg-RW
                       start-pj
                       he-WI
                       zg-he
                       pj-fs
                       start-RW
                       """;

        assertEquals(3509, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(91533, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2021/day12/input"))));
    }
}