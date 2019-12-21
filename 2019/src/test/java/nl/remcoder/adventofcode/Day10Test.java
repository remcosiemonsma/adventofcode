package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {
    private Day10 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day10();
    }

    @Test
    void testPart1Case1() {
        Stream<String> input = Stream.of(".#..#", ".....", "#####", "....#", "...##");

        assertEquals(8, testSubject.handlePart1(input));
    }

    @Test
    void testPart1Case2() {
        Stream<String> input = Stream.of("......#.#.",
                                         "#..#.#....",
                                         "..#######.",
                                         ".#.#.###..",
                                         ".#..#.....",
                                         "..#....#.#",
                                         "#..#....#.",
                                         ".##.#..###",
                                         "##...#..#.",
                                         ".#....####");

        assertEquals(33, testSubject.handlePart1(input));
    }

    @Test
    void testPart1Case3() {
        Stream<String> input = Stream.of("#.#...#.#.",
                                         ".###....#.",
                                         ".#....#...",
                                         "##.#.#.#.#",
                                         "....#.#.#.",
                                         ".##..###.#",
                                         "..#...##..",
                                         "..##....##",
                                         "......#...",
                                         ".####.###.");

        assertEquals(35, testSubject.handlePart1(input));
    }

    @Test
    void testPart1Case4() {
        Stream<String> input = Stream.of(".#..#..###",
                                         "####.###.#",
                                         "....###.#.",
                                         "..###.##.#",
                                         "##.##.#.#.",
                                         "....###..#",
                                         "..#.#..#.#",
                                         "#..#.#.###",
                                         ".##...##.#",
                                         ".....#.#..");

        assertEquals(41, testSubject.handlePart1(input));
    }

    @Test
    void testPart1Case5() {
        Stream<String> input = Stream.of(".#..##.###...#######",
                                         "##.############..##.",
                                         ".#.######.########.#",
                                         ".###.#######.####.#.",
                                         "#####.##.#.##.###.##",
                                         "..#####..#.#########",
                                         "####################",
                                         "#.####....###.#.#.##",
                                         "##.#################",
                                         "#####.##.###..####..",
                                         "..######..##.#######",
                                         "####.##.####...##..#",
                                         ".#####..#.######.###",
                                         "##...#.##########...",
                                         "#.##########.#######",
                                         ".####.#.###.###.#.##",
                                         "....##.##.###..#####",
                                         ".#.#.###########.###",
                                         "#.#.#.#####.####.###",
                                         "###.##.####.##.#..##");

        assertEquals(210, testSubject.handlePart1(input));
    }

    @Test
    void testPart1Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()));

        assertEquals(282, testSubject.handlePart1(input));
    }

    @Test
    void testPart2Case1 () {
        Stream<String> input = Stream.of(".#..##.###...#######",
                                         "##.############..##.",
                                         ".#.######.########.#",
                                         ".###.#######.####.#.",
                                         "#####.##.#.##.###.##",
                                         "..#####..#.#########",
                                         "####################",
                                         "#.####....###.#.#.##",
                                         "##.#################",
                                         "#####.##.###..####..",
                                         "..######..##.#######",
                                         "####.##.####...##..#",
                                         ".#####..#.######.###",
                                         "##...#.##########...",
                                         "#.##########.#######",
                                         ".####.#.###.###.#.##",
                                         "....##.##.###..#####",
                                         ".#.#.###########.###",
                                         "#.#.#.#####.####.###",
                                         "###.##.####.##.#..##");

        assertEquals(802, testSubject.handlePart2(input));
    }

    @Test
    void testPart2Input() throws Exception {
        Stream<String> input = Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()));

        assertEquals(1008, testSubject.handlePart2(input));
    }
}
