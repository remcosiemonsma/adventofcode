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
    void testPart1Case1() {
        var input = """
                             A          \s
                             A          \s
                      #######.######### \s
                      #######.........# \s
                      #######.#######.# \s
                      #######.#######.# \s
                      #######.#######.# \s
                      #####  B    ###.# \s
                    BC...##  C    ###.# \s
                      ##.##       ###.# \s
                      ##...DE  F  ###.# \s
                      #####    G  ###.# \s
                      #########.#####.# \s
                    DE..#######...###.# \s
                      #.#########.###.# \s
                    FG..#########.....# \s
                      ###########.##### \s
                                 Z      \s
                                 Z      \s
                    """;

        assertEquals(23, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        var input = """
                                       A              \s
                                       A              \s
                      #################.############# \s
                      #.#...#...................#.#.# \s
                      #.#.#.###.###.###.#########.#.# \s
                      #.#.#.......#...#.....#.#.#...# \s
                      #.#########.###.#####.#.#.###.# \s
                      #.............#.#.....#.......# \s
                      ###.###########.###.#####.#.#.# \s
                      #.....#        A   C    #.#.#.# \s
                      #######        S   P    #####.# \s
                      #.#...#                 #......VT
                      #.#.#.#                 #.##### \s
                      #...#.#               YN....#.# \s
                      #.###.#                 #####.# \s
                    DI....#.#                 #.....# \s
                      #####.#                 #.###.# \s
                    ZZ......#               QG....#..AS
                      ###.###                 ####### \s
                    JO..#.#.#                 #.....# \s
                      #.#.#.#                 ###.#.# \s
                      #...#..DI             BU....#..LF
                      #####.#                 #.##### \s
                    YN......#               VT..#....QG
                      #.###.#                 #.###.# \s
                      #.#...#                 #.....# \s
                      ###.###    J L     J    #.#.### \s
                      #.....#    O F     P    #.#...# \s
                      #.###.#####.#.#####.#####.###.# \s
                      #...#.#.#...#.....#.....#.#...# \s
                      #.#####.###.###.#.#.#########.# \s
                      #...#.#.....#...#.#.#.#.....#.# \s
                      #.###.#####.###.###.#.#.####### \s
                      #.#.........#...#.............# \s
                      #########.###.###.############# \s
                               B   J   C              \s
                               U   P   P              \s
                    """;

        assertEquals(58, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(618, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day20/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        var input = """
                             A          \s
                             A          \s
                      #######.######### \s
                      #######.........# \s
                      #######.#######.# \s
                      #######.#######.# \s
                      #######.#######.# \s
                      #####  B    ###.# \s
                    BC...##  C    ###.# \s
                      ##.##       ###.# \s
                      ##...DE  F  ###.# \s
                      #####    G  ###.# \s
                      #########.#####.# \s
                    DE..#######...###.# \s
                      #.#########.###.# \s
                    FG..#########.....# \s
                      ###########.##### \s
                                 Z      \s
                                 Z      \s
                    """;

        assertEquals(26, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Case2() {
        var input = """
                                 Z L X W       C                \s
                                 Z P Q B       K                \s
                      ###########.#.#.#.#######.############### \s
                      #...#.......#.#.......#.#.......#.#.#...# \s
                      ###.#.#.#.#.#.#.#.###.#.#.#######.#.#.### \s
                      #.#...#.#.#...#.#.#...#...#...#.#.......# \s
                      #.###.#######.###.###.#.###.###.#.####### \s
                      #...#.......#.#...#...#.............#...# \s
                      #.#########.#######.#.#######.#######.### \s
                      #...#.#    F       R I       Z    #.#.#.# \s
                      #.###.#    D       E C       H    #.#.#.# \s
                      #.#...#                           #...#.# \s
                      #.###.#                           #.###.# \s
                      #.#....OA                       WB..#.#..ZH
                      #.###.#                           #.#.#.# \s
                    CJ......#                           #.....# \s
                      #######                           ####### \s
                      #.#....CK                         #......IC
                      #.###.#                           #.###.# \s
                      #.....#                           #...#.# \s
                      ###.###                           #.#.#.# \s
                    XF....#.#                         RF..#.#.# \s
                      #####.#                           ####### \s
                      #......CJ                       NM..#...# \s
                      ###.#.#                           #.###.# \s
                    RE....#.#                           #......RF
                      ###.###        X   X       L      #.#.#.# \s
                      #.....#        F   Q       P      #.#.#.# \s
                      ###.###########.###.#######.#########.### \s
                      #.....#...#.....#.......#...#.....#.#...# \s
                      #####.#.###.#######.#######.###.###.#.#.# \s
                      #.......#.......#.#.#.#.#...#...#...#.#.# \s
                      #####.###.#####.#.#.#.#.###.###.#.###.### \s
                      #.......#.....#.#...#...............#...# \s
                      #############.#.#.###.################### \s
                                   A O F   N                    \s
                                   A A D   M                    \s
                    """;

        assertEquals(396, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(7152, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day20/input").toURI()))));
    }
}