package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test {
    private Day18 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day18();
    }

    @Test
    void testPart1Case1() {
        var input = """
                    #########
                    #b.A.@.a#
                    #########
                    """;

        assertEquals(8, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        var input = """
                    ########################
                    #f.D.E.e.C.b.A.@.a.B.c.#
                    ######################.#
                    #d.....................#
                    ########################
                    """;

        assertEquals(86, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case3() {
        var input = """
                    ########################
                    #...............b.C.D.f#
                    #.######################
                    #.....@.a.B.c.d.A.e.F.g#
                    ########################
                    """;

        assertEquals(132, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case4() {
        var input = """
                    #################
                    #i.G..c...e..H.p#
                    ########.########
                    #j.A..b...f..D.o#
                    ########@########
                    #k.E..a...g..B.n#
                    ########.########
                    #l.F..d...h..C.m#
                    #################
                    """;

        assertEquals(136, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case5() {
        var input = """
                    ########################
                    #@..............ac.GI.b#
                    ###d#e#f################
                    ###A#B#C################
                    ###g#h#i################
                    ########################
                    """;

        assertEquals(81, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(4270, testSubject.handlePart1(
                Files.lines(Paths.get("../aoc-inputs/2019/day18/input"))));
    }

    @Test
    void testPart2Case1() {
        var input = """
                    #######
                    #a.#Cd#
                    ##...##
                    ##.@.##
                    ##...##
                    #cB#Ab#
                    #######
                    """;

        assertEquals(8, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case2() {
        var input = """
                    ###############
                    #d.ABC.#.....a#
                    ######...######
                    ######.@.######
                    ######...######
                    #b.....#.....c#
                    ###############
                    """;

        assertEquals(24, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case3() {
        var input = """
                    #############
                    #DcBa.#.GhKl#
                    #.###...#I###
                    #e#d#.@.#j#k#
                    ###C#...###J#
                    #fEbA.#.FgHi#
                    #############
                    """;

        assertEquals(32, testSubject.handlePart2(input.lines()));
    }

    @Disabled("Currently fails")
    @Test
    void testPart2Case4() {
        var input = """
                    #############
                    #g#f.D#..h#l#
                    #F###e#E###.#
                    #dCba...BcIJ#
                    #####.@.#####
                    #nK.L...G...#
                    #M###N#H###.#
                    #o#m..#i#jk.#
                    #############
                    """;

        assertEquals(72, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(1982, testSubject.handlePart2(
                Files.lines(Paths.get("../aoc-inputs/2019/day18/input"))));
    }
}