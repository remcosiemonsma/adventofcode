package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day20Test {
    private Day20 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day20();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       Tile 2311:
                       ..##.#..#.
                       ##..#.....
                       #...##..#.
                       ####.#...#
                       ##.##.###.
                       ##...#.###
                       .#.#.#..##
                       ..#....#..
                       ###...#.#.
                       ..###..###
                                       
                       Tile 1951:
                       #.##...##.
                       #.####...#
                       .....#..##
                       #...######
                       .##.#....#
                       .###.#####
                       ###.##.##.
                       .###....#.
                       ..#.#..#.#
                       #...##.#..
                                       
                       Tile 1171:
                       ####...##.
                       #..##.#..#
                       ##.#..#.#.
                       .###.####.
                       ..###.####
                       .##....##.
                       .#...####.
                       #.##.####.
                       ####..#...
                       .....##...
                                       
                       Tile 1427:
                       ###.##.#..
                       .#..#.##..
                       .#.##.#..#
                       #.#.#.##.#
                       ....#...##
                       ...##..##.
                       ...#.#####
                       .#.####.#.
                       ..#..###.#
                       ..##.#..#.
                                       
                       Tile 1489:
                       ##.#.#....
                       ..##...#..
                       .##..##...
                       ..#...#...
                       #####...#.
                       #..#.#.#.#
                       ...#.#.#..
                       ##.#...##.
                       ..##.##.##
                       ###.##.#..
                                       
                       Tile 2473:
                       #....####.
                       #..#.##...
                       #.##..#...
                       ######.#.#
                       .#...#.#.#
                       .#########
                       .###.#..#.
                       ########.#
                       ##...##.#.
                       ..###.#.#.
                                       
                       Tile 2971:
                       ..#.#....#
                       #...###...
                       #.#.###...
                       ##.##..#..
                       .#####..##
                       .#..####.#
                       #..#.#..#.
                       ..####.###
                       ..#.#.###.
                       ...#.#.#.#
                                       
                       Tile 2729:
                       ...#.#.#.#
                       ####.#....
                       ..#.#.....
                       ....#..#.#
                       .##..##.#.
                       .#.####...
                       ####.#.#..
                       ##.####...
                       ##..#.##..
                       #.##...##.
                                       
                       Tile 3079:
                       #.#.#####.
                       .#..######
                       ..#.......
                       ######....
                       ####.#..#.
                       .#...#.##.
                       #.#####.##
                       ..#.###...
                       ..#.......
                       ..#.###...
                       """;

        assertEquals(20899048083289L, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(59187348943703L, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day20/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       Tile 2311:
                       ..##.#..#.
                       ##..#.....
                       #...##..#.
                       ####.#...#
                       ##.##.###.
                       ##...#.###
                       .#.#.#..##
                       ..#....#..
                       ###...#.#.
                       ..###..###
                                       
                       Tile 1951:
                       #.##...##.
                       #.####...#
                       .....#..##
                       #...######
                       .##.#....#
                       .###.#####
                       ###.##.##.
                       .###....#.
                       ..#.#..#.#
                       #...##.#..
                                       
                       Tile 1171:
                       ####...##.
                       #..##.#..#
                       ##.#..#.#.
                       .###.####.
                       ..###.####
                       .##....##.
                       .#...####.
                       #.##.####.
                       ####..#...
                       .....##...
                                       
                       Tile 1427:
                       ###.##.#..
                       .#..#.##..
                       .#.##.#..#
                       #.#.#.##.#
                       ....#...##
                       ...##..##.
                       ...#.#####
                       .#.####.#.
                       ..#..###.#
                       ..##.#..#.
                                       
                       Tile 1489:
                       ##.#.#....
                       ..##...#..
                       .##..##...
                       ..#...#...
                       #####...#.
                       #..#.#.#.#
                       ...#.#.#..
                       ##.#...##.
                       ..##.##.##
                       ###.##.#..
                                       
                       Tile 2473:
                       #....####.
                       #..#.##...
                       #.##..#...
                       ######.#.#
                       .#...#.#.#
                       .#########
                       .###.#..#.
                       ########.#
                       ##...##.#.
                       ..###.#.#.
                                       
                       Tile 2971:
                       ..#.#....#
                       #...###...
                       #.#.###...
                       ##.##..#..
                       .#####..##
                       .#..####.#
                       #..#.#..#.
                       ..####.###
                       ..#.#.###.
                       ...#.#.#.#
                                       
                       Tile 2729:
                       ...#.#.#.#
                       ####.#....
                       ..#.#.....
                       ....#..#.#
                       .##..##.#.
                       .#.####...
                       ####.#.#..
                       ##.####...
                       ##..#.##..
                       #.##...##.
                                       
                       Tile 3079:
                       #.#.#####.
                       .#..######
                       ..#.......
                       ######....
                       ####.#..#.
                       .#...#.##.
                       #.#####.##
                       ..#.###...
                       ..#.......
                       ..#.###...
                       """;

        assertEquals(273, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(1565, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day20/input").toURI()))));
    }
}