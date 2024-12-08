package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {
    private Day25 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day25();
    }

    @Test
    void part1Case1() {
        String data = """
                      jqt: rhn xhk nvd
                      rsh: frs pzl lsr
                      xhk: hfx
                      cmg: qnr nvd lhk bvb
                      rhn: xhk bvb hfx
                      bvb: xhk hfx
                      pzl: lsr hfx nvd
                      qnr: nvd
                      ntq: jqt hfx bvb xhk
                      nvd: lhk
                      lsr: lhk
                      rzs: qnr cmg lsr rsh
                      frs: qnr lhk lsr
                      """;

        assertEquals(54, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(614655, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day25/input"))));
    }
}