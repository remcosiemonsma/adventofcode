package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day23Test {
    private Day23 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day23();
    }

    @Test
    void part1Case1() {
        String data = """
                      kh-tc
                      qp-kh
                      de-cg
                      ka-co
                      yn-aq
                      qp-ub
                      cg-tb
                      vc-aq
                      tb-ka
                      wh-tc
                      yn-cg
                      kh-ub
                      ta-co
                      de-co
                      tc-td
                      tb-wq
                      wh-td
                      ta-ka
                      td-qp
                      aq-cg
                      wq-ub
                      ub-vc
                      de-ta
                      wq-aq
                      wq-vc
                      wh-yn
                      ka-de
                      kh-ta
                      co-tc
                      wh-qp
                      tb-vc
                      td-yn
                      """;

        assertEquals(7, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1323, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day23/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      kh-tc
                      qp-kh
                      de-cg
                      ka-co
                      yn-aq
                      qp-ub
                      cg-tb
                      vc-aq
                      tb-ka
                      wh-tc
                      yn-cg
                      kh-ub
                      ta-co
                      de-co
                      tc-td
                      tb-wq
                      wh-td
                      ta-ka
                      td-qp
                      aq-cg
                      wq-ub
                      ub-vc
                      de-ta
                      wq-aq
                      wq-vc
                      wh-yn
                      ka-de
                      kh-ta
                      co-tc
                      wh-qp
                      tb-vc
                      td-yn
                      """;

        assertEquals("co,de,ka,ta", testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("er,fh,fi,ir,kk,lo,lp,qi,ti,vb,xf,ys,yu", testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day23/input"))));
    }
}