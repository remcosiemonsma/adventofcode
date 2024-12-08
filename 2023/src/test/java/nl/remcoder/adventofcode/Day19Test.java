package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day19Test {
    private Day19 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day19();
    }

    @Test
    void part1Case1() {
        String data = """
                      px{a<2006:qkq,m>2090:A,rfg}
                      pv{a>1716:R,A}
                      lnx{m>1548:A,A}
                      rfg{s<537:gd,x>2440:R,A}
                      qs{s>3448:A,lnx}
                      qkq{x<1416:A,crn}
                      crn{x>2662:A,R}
                      in{s<1351:px,qqz}
                      qqz{s>2770:qs,m<1801:hdj,R}
                      gd{a>3333:R,R}
                      hdj{m>838:A,pv}
                      
                      {x=787,m=2655,a=1222,s=2876}
                      {x=1679,m=44,a=2067,s=496}
                      {x=2036,m=264,a=79,s=2244}
                      {x=2461,m=1339,a=466,s=291}
                      {x=2127,m=1623,a=2188,s=1013}
                      """;

        assertEquals(19114, testSubject.handlePart1(data.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(446517, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2023/day19/input"))));
    }

    @Test
    void part2Case1() {
        String data = """
                      px{a<2006:qkq,m>2090:A,rfg}
                      pv{a>1716:R,A}
                      lnx{m>1548:A,A}
                      rfg{s<537:gd,x>2440:R,A}
                      qs{s>3448:A,lnx}
                      qkq{x<1416:A,crn}
                      crn{x>2662:A,R}
                      in{s<1351:px,qqz}
                      qqz{s>2770:qs,m<1801:hdj,R}
                      gd{a>3333:R,R}
                      hdj{m>838:A,pv}
                      
                      {x=787,m=2655,a=1222,s=2876}
                      {x=1679,m=44,a=2067,s=496}
                      {x=2036,m=264,a=79,s=2244}
                      {x=2461,m=1339,a=466,s=291}
                      {x=2127,m=1623,a=2188,s=1013}
                      """;

        assertEquals(167409079868000L, testSubject.handlePart2(data.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(130090458884662L, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2023/day19/input"))));
    }
}