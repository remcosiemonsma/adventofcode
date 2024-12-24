package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    private Day24 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day24();
    }

    @Test
    void part1Case1() {
        String data = """
                      x00: 1
                      x01: 1
                      x02: 1
                      y00: 0
                      y01: 1
                      y02: 0
                      
                      x00 AND y00 -> z00
                      x01 XOR y01 -> z01
                      x02 OR y02 -> z02
                      """;

        assertEquals(4, testSubject.handlePart1(data.lines()));
    }

    @Test
    void part1Case2() {
        String data = """
                      x00: 1
                      x01: 0
                      x02: 1
                      x03: 1
                      x04: 0
                      y00: 1
                      y01: 1
                      y02: 1
                      y03: 1
                      y04: 1
                      
                      ntg XOR fgs -> mjb
                      y02 OR x01 -> tnw
                      kwq OR kpj -> z05
                      x00 OR x03 -> fst
                      tgd XOR rvg -> z01
                      vdt OR tnw -> bfw
                      bfw AND frj -> z10
                      ffh OR nrd -> bqk
                      y00 AND y03 -> djm
                      y03 OR y00 -> psh
                      bqk OR frj -> z08
                      tnw OR fst -> frj
                      gnj AND tgd -> z11
                      bfw XOR mjb -> z00
                      x03 OR x00 -> vdt
                      gnj AND wpb -> z02
                      x04 AND y00 -> kjc
                      djm OR pbm -> qhw
                      nrd AND vdt -> hwm
                      kjc AND fst -> rvg
                      y04 OR y02 -> fgs
                      y01 AND x02 -> pbm
                      ntg OR kjc -> kwq
                      psh XOR fgs -> tgd
                      qhw XOR tgd -> z09
                      pbm OR djm -> kpj
                      x03 XOR y03 -> ffh
                      x00 XOR y04 -> ntg
                      bfw OR bqk -> z06
                      nrd XOR fgs -> wpb
                      frj XOR qhw -> z04
                      bqk OR frj -> z07
                      y03 OR x01 -> nrd
                      hwm AND bqk -> z03
                      tgd XOR rvg -> z12
                      tnw OR pbm -> gnj
                      """;

        assertEquals(2024, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(69201640933606L, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2024/day24/input"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals("dhq,hbs,jcp,kfp,pdg,z18,z22,z27", testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2024/day24/input"))));
    }
}